package com.bank.central.servlet.courant;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;

import com.bank.courant.remote.CompteRemote;
import com.bank.courant.remote.ParticulierRemote;
import com.bank.courant.model.Compte;
import com.bank.courant.model.CompteComplet;
import com.bank.courant.model.Particulier;

import java.util.List;


@WebServlet("/compte")
public class CompteService extends HttpServlet {
    
    @EJB(lookup = "java:global/courant-1.0.0/CompteService!com.bank.courant.remote.CompteRemote")
    CompteRemote compteService;

    @EJB(lookup = "java:global/courant-1.0.0/ParticulierService!com.bank.courant.remote.ParticulierRemote")
    private ParticulierRemote particulierService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }    
        try {
            switch(action){
                case  "list":
                    listComptes(request, response);
                    break;
                case "create":
                    formComptes(request, response);
                    break;
                case "view" :
                    viewCompte(request,response);
                default :
                    listComptes(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
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
            switch(action){
                case  "list":
                    listComptes(request, response);
                    break;
                case "create":
                    createComptes(request, response);
                    break;
                default :
                    listComptes(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
            
    }

    


    private void listComptes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("comptes", compteService.getAllComptes());
            request.getRequestDispatcher("/courant/compte/list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void formComptes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Particulier> particuliers = particulierService.getAllParticuliers();
            request.setAttribute("particuliers", particuliers);
            request.getRequestDispatcher("/courant/compte/form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void viewCompte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            CompteComplet c = compteService.findById(id);
            request.setAttribute("compteComplet", c);
            request.getRequestDispatcher("/courant/compte/view.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);  
        }
    
    }

    
    private void createComptes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        try {
            String idParticulierStr = request.getParameter("idParticulier");
            String dateOuvertureStr = request.getParameter("dateOuverture");
            String montantDecouvertStr = request.getParameter("montantDecouvert");
            String plafondStr = request.getParameter("plafond");
            
            // Validation
            if (idParticulierStr == null || idParticulierStr.trim().isEmpty() || 
                dateOuvertureStr == null || dateOuvertureStr.trim().isEmpty()) {
                
                List<Particulier> particuliers = particulierService.getAllParticuliers();
                request.setAttribute("particuliers", particuliers);
                request.setAttribute("errorMessage", "Les champs Particulier et Date d'Ouverture sont obligatoires");
                request.getRequestDispatcher("/courant/compte/create.jsp").forward(request, response);
                return;
            }
            CompteComplet c = compteService.createCompte(
                idParticulierStr, dateOuvertureStr, montantDecouvertStr, plafondStr);
            
            response.sendRedirect(request.getContextPath() + "/compte?action=list");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    
    
    }

    




}
