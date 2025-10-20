package com.bank.central.servlet.pret;

import com.bank.pret.remote.PretServiceRemote;
import com.bank.courant.remote.ParticulieServiceRemote;
import com.bank.pret.model.Compte;
import com.bank.courant.model.Particulie;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ComptePretServlet", value = "/pret/comptes")
public class ComptePretServlet extends HttpServlet {
    
    @EJB(lookup = "java:global/pret-1.0.0/PretService!com.bank.pret.remote.PretServiceRemote")
    private PretServiceRemote pretService;
    
    @EJB(lookup = "java:global/courant-1.0.0/ParticulieService!com.bank.courant.remote.ParticulieServiceRemote")
    private ParticulieServiceRemote particulieService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "view":
                    viewAccount(request, response);
                    break;
                case "search":
                    searchAccount(request, response);
                    break;
                default:
                    listAllAccounts(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("/pret/error.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "create":
                    createAccount(request, response);
                    break;
                case "searchById":
                    searchAccountById(request, response);
                    break;
                case "searchByNumero":
                    searchAccountByNumero(request, response);
                    break;
                default:
                    listAllAccounts(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("/pret/error.jsp").forward(request, response);
        }
    }
    
    private void listAllAccounts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Compte> comptes = pretService.findAllAccounts();
        request.setAttribute("comptes", comptes);
        request.getRequestDispatcher("/pret/compte/list.jsp").forward(request, response);
    }
    
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Particulie> particuliers = particulieService.findAll();
        request.setAttribute("particuliers", particuliers);
        request.getRequestDispatcher("/pret/compte/create.jsp").forward(request, response);
    }
    
    private void createAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idParticulier = Integer.parseInt(request.getParameter("idParticulier"));
            
            // Vérifier si le particulier existe
            Particulie particulier = particulieService.find(idParticulier);
            if (particulier == null) {
                request.setAttribute("error", "Particulier non trouvé avec l'ID: " + idParticulier);
                List<Particulie> particuliers = particulieService.findAll();
                request.setAttribute("particuliers", particuliers);
                request.getRequestDispatcher("/pret/compte/create.jsp").forward(request, response);
                return;
            }
            
            Compte compte = pretService.createLoanAccount(idParticulier);
            
            request.setAttribute("success", "Compte créé avec succès! Numéro: " + compte.getNumero());
            request.setAttribute("compte", compte);
            request.setAttribute("particulier", particulier);
            request.getRequestDispatcher("/pret/compte/created.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création du compte: " + e.getMessage());
            List<Particulie> particuliers = particulieService.findAll();
            request.setAttribute("particuliers", particuliers);
            request.getRequestDispatcher("/pret/compte/create.jsp").forward(request, response);
        }
    }
    
    private void viewAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Compte compte = pretService.findAccountById(id);
            
            if (compte != null) {
                request.setAttribute("compte", compte);
                request.getRequestDispatcher("/pret/compte/view.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Compte non trouvé avec l'ID: " + id);
                listAllAccounts(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID invalide");
            listAllAccounts(request, response);
        }
    }
    
    private void searchAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pret/compte/search.jsp").forward(request, response);
    }
    
    private void searchAccountById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Compte compte = pretService.findAccountById(id);
            
            if (compte != null) {
                request.setAttribute("compte", compte);
                request.setAttribute("searchType", "id");
                request.setAttribute("searchValue", String.valueOf(id));
            } else {
                request.setAttribute("error", "Aucun compte trouvé avec l'ID: " + id);
            }
            
            request.getRequestDispatcher("/pret/compte/search.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID invalide");
            request.getRequestDispatcher("/pret/compte/search.jsp").forward(request, response);
        }
    }
    
    private void searchAccountByNumero(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numero = request.getParameter("numero");
        Compte compte = pretService.findAccountByNumero(numero);
        
        if (compte != null) {
            request.setAttribute("compte", compte);
            request.setAttribute("searchType", "numero");
            request.setAttribute("searchValue", numero);
        } else {
            request.setAttribute("error", "Aucun compte trouvé avec le numéro: " + numero);
        }
        
        request.getRequestDispatcher("/pret/compte/search.jsp").forward(request, response);
    }
}