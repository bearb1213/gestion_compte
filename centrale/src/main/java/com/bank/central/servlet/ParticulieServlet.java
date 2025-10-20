package com.bank.central.servlet;

import jakarta.servlet.annotation.*;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.bank.courant.model.Particulie;
import com.bank.courant.remote.ParticulieServiceRemote;

@WebServlet("/particulie")
@MultipartConfig
public class ParticulieServlet extends HttpServlet {

    @EJB(lookup = "java:global/courant-1.0.0/ParticulieService!com.bank.courant.remote.ParticulieServiceRemote")
    private ParticulieServiceRemote particulieService;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteParticulie(request, response);
                    break;
                case "view":
                    viewParticulie(request, response);
                    break;
                default:
                    listParticulie(request, response);
                    break;
            }
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors du traitement de la requête");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "insert":
                    insertParticulie(request, response);
                    break;
                case "update":
                    updateParticulie(request, response);
                    break;
                default:
                    listParticulie(request, response);
                    break;
            }
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors du traitement du formulaire");
        }
    }

    private void listParticulie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Particulie> listParticulie = particulieService.findAll();
            request.setAttribute("listParticulie", listParticulie);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/particulie-list.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération de la liste des particuliers", e);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/particulie-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Particulie existingParticulie = particulieService.find(id);
            
            if (existingParticulie == null) {
                response.sendRedirect("particulie?action=list&error=notfound");
                return;
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/particulie-form.jsp");
            request.setAttribute("particulie", existingParticulie);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération du particulier", e);
        }
    }

    private void viewParticulie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Particulie particulie = particulieService.find(id);
            
            if (particulie == null) {
                response.sendRedirect("particulie?action=list&error=notfound");
                return;
            }
            
            request.setAttribute("particulie", particulie);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/particulie-view.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'affichage du particulier", e);
        }
    }

    private void insertParticulie(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        try {
            // Validation des champs requis
            String cin = request.getParameter("cin");
            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String dateNaissanceStr = request.getParameter("dateNaissance");
            
            if (cin == null || cin.trim().isEmpty() || 
                nom == null || nom.trim().isEmpty() || 
                email == null || email.trim().isEmpty() ||
                dateNaissanceStr == null || dateNaissanceStr.trim().isEmpty()) {
                
                request.setAttribute("error", "Les champs CIN, Nom, Email et Date de Naissance sont obligatoires");
                request.getRequestDispatcher("/particulie-form.jsp").forward(request, response);
                return;
            }

            // Vérifier si le CIN existe déjà
            Particulie existingByCin = particulieService.findByCin(cin);
            if (existingByCin != null) {
                request.setAttribute("error", "Un particulier avec ce CIN existe déjà");
                request.setAttribute("particulie", createParticulieFromRequest(request));
                request.getRequestDispatcher("/particulie-form.jsp").forward(request, response);
                return;
            }

            // Vérifier si l'email existe déjà
            // Note: Vous devrez peut-être ajouter une méthode findByEmail dans votre service
            // Pour l'instant, on suppose que l'unicité est gérée par l'EJB

            Particulie newParticulie = createParticulieFromRequest(request);
            particulieService.create(newParticulie);
            
            response.sendRedirect("particulie?action=list&success=create");
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création: " + e.getMessage());
            request.setAttribute("particulie", createParticulieFromRequest(request));
            request.getRequestDispatcher("/particulie-form.jsp").forward(request, response);
        }
    }

    private void updateParticulie(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            // Vérifier si le particulier existe
            Particulie existingParticulie = particulieService.find(id);
            if (existingParticulie == null) {
                response.sendRedirect("particulie?action=list&error=notfound");
                return;
            }

            // Validation des champs requis
            String cin = request.getParameter("cin");
            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String dateNaissanceStr = request.getParameter("dateNaissance");
            
            if (cin == null || cin.trim().isEmpty() || 
                nom == null || nom.trim().isEmpty() || 
                email == null || email.trim().isEmpty() ||
                dateNaissanceStr == null || dateNaissanceStr.trim().isEmpty()) {
                
                request.setAttribute("error", "Les champs CIN, Nom, Email et Date de Naissance sont obligatoires");
                request.setAttribute("particulie", createParticulieFromRequest(request));
                request.getRequestDispatcher("/particulie-form.jsp").forward(request, response);
                return;
            }

            // Vérifier si le CIN existe déjà pour un autre utilisateur
            Particulie existingByCin = particulieService.findByCin(cin);
            if (existingByCin != null && existingByCin.getId() != id) {
                request.setAttribute("error", "Un autre particulier avec ce CIN existe déjà");
                request.setAttribute("particulie", createParticulieFromRequest(request));
                request.getRequestDispatcher("/particulie-form.jsp").forward(request, response);
                return;
            }

            Particulie updatedParticulie = createParticulieFromRequest(request);
            updatedParticulie.setId(id);
            
            particulieService.update(updatedParticulie);
            response.sendRedirect("particulie?action=list&success=update");
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la modification: " + e.getMessage());
            request.setAttribute("particulie", createParticulieFromRequest(request));
            request.getRequestDispatcher("/particulie-form.jsp").forward(request, response);
        }
    }

    private void deleteParticulie(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            // Vérifier si le particulier existe
            Particulie existingParticulie = particulieService.find(id);
            if (existingParticulie == null) {
                response.sendRedirect("particulie?action=list&error=notfound");
                return;
            }
            
            particulieService.delete(id);
            response.sendRedirect("particulie?action=list&success=delete");
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors de la suppression");
        }
    }

    private Particulie createParticulieFromRequest(HttpServletRequest request) {
        Particulie particulie = new Particulie();
        
        particulie.setCin(request.getParameter("cin"));
        particulie.setNom(request.getParameter("nom"));
        particulie.setPrenom(request.getParameter("prenom"));
        
        String dateNaissanceStr = request.getParameter("dateNaissance");
        if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {
            particulie.setDateNaissance(LocalDate.parse(dateNaissanceStr, formatter));
        }
        
        particulie.setAdresse(request.getParameter("adresse"));
        particulie.setTelephone(request.getParameter("telephone"));
        particulie.setEmail(request.getParameter("email"));
        
        return particulie;
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e, String message)
            throws ServletException, IOException {
        e.printStackTrace(); // Log l'erreur
        request.setAttribute("error", message + ": " + e.getMessage());
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}