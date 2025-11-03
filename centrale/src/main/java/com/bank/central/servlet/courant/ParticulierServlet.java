package com.bank.central.servlet.courant;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;

import jakarta.ejb.EJB;

import com.bank.courant.model.Particulier;
import com.bank.courant.remote.ParticulierRemote;

@WebServlet("/particulier")
public class ParticulierServlet extends HttpServlet{
    
    @EJB(lookup = "java:global/courant-1.0.0/ParticulierService!com.bank.courant.remote.ParticulierRemote")
    private ParticulierRemote particulierService;

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
    
    
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "list":
                    listParticuliers(request, response);
                    break;
                case "create":
                    formParticulier(request, response);
                    break;
                case "edit":
                    formEditParticulier(request, response);
                default:
                    listParticuliers(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        
        }    
    }

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
    
    
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "list":
                    listParticuliers(request, response);
                    break;
                case "create":
                    createParticulier(request, response);
                    break;
                case "edit":
                    editParticulier(request, response);
                default:
                    listParticuliers(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        
        }    
    }

    private void listParticuliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("particuliers", particulierService.getAllParticuliers());
            request.getRequestDispatcher("/courant/particulier/list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void formParticulier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.getRequestDispatcher("/courant/particulier/form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
    private void formEditParticulier(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String idParam = request.getParameter("id");
            int id= Integer.parseInt(idParam);
            Particulier particulier = particulierService.getParticulierById(id);
            request.setAttribute("particulier", particulier);
            request.getRequestDispatcher("/courant/particulier/formEdit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }



    }


    private void createParticulier(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String cin = request.getParameter("cin");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String dateNaissanceStr = request.getParameter("dateNaissance");
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("email");
            
            
            if (cin == null || cin.trim().isEmpty() || 
            nom == null || nom.trim().isEmpty() || 
            dateNaissanceStr == null || dateNaissanceStr.trim().isEmpty()) {
                
                request.setAttribute("errorMessage", "Les champs CIN, Nom et Date de Naissance sont obligatoires");
                request.getRequestDispatcher("/courant/particulier/edit.jsp").forward(request, response);
                return;
            }
            
            Particulier particulier = particulierService.createParticulier(
                cin, nom, prenom, dateNaissanceStr, adresse, telephone, email);
                
                response.sendRedirect(request.getContextPath() + "/particulier?action=list");
         } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

    
    
    }

    private void editParticulier(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/particulier?action=list");
                return;
            }

            String cin = request.getParameter("cin");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String dateNaissanceStr = request.getParameter("dateNaissance");
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("email");


            int id = Integer.parseInt(idParam);
             if (cin == null || cin.trim().isEmpty() || 
            nom == null || nom.trim().isEmpty() || 
            dateNaissanceStr == null || dateNaissanceStr.trim().isEmpty()) {
        
                Particulier particulier = particulierService.getParticulierById(id);
                request.setAttribute("particulier", particulier);
                request.setAttribute("errorMessage", "Les champs CIN, Nom et Date de Naissance sont obligatoires");
                request.getRequestDispatcher("/courant/particulier/edit.jsp").forward(request, response);
                return;
            }
        
            
            // Appel du service de mise à jour
            Particulier updatedParticulier = particulierService.updateParticulier(
                idParam, cin, nom, prenom, dateNaissanceStr, adresse, telephone, email);
            
            // Redirection vers la liste en cas de succès
            response.sendRedirect(request.getContextPath() + "/particulier?action=list");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }



    

}
