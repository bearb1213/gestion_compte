package com.bank.central.servlet;

import jakarta.servlet.annotation.*;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

import com.bank.courant.model.Compte;
import com.bank.courant.remote.CompteServiceRemote;

@WebServlet("/operation")
@MultipartConfig
public class OperationServlet extends HttpServlet {
    
    @EJB(lookup = "java:global/courant-1.0.0/CompteService!com.bank.courant.remote.CompteServiceRemote")
    private CompteServiceRemote compteService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String numeroCompte = request.getParameter("numero");
        
        if (numeroCompte == null) {
            response.sendRedirect("compte?action=list&error=nocompte");
            return;
        }

        try {
            Compte compte = compteService.findAccountByNumero(numeroCompte);
            if (compte == null) {
                response.sendRedirect("compte?action=list&error=notfound");
                return;
            }

            request.setAttribute("compte", compte);
            
            switch (action) {
                case "depot":
                    request.getRequestDispatcher("/courant/operation-depot.jsp").forward(request, response);
                    break;
                case "retrait":
                    request.getRequestDispatcher("/courant/operation-retrait.jsp").forward(request, response);
                    break;
                case "transfert":
                    request.getRequestDispatcher("/courant/operation-transfert.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("compte?action=view&numero=" + numeroCompte);
                    break;
            }
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors du chargement de l'opération");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String numeroCompte = request.getParameter("numeroCompte");
        
        try {
            Compte compte = compteService.findAccountByNumero(numeroCompte);
            if (compte == null) {
                response.sendRedirect("compte?action=list&error=notfound");
                return;
            }

            switch (action) {
                case "depot":
                    processDepot(request, response, compte);
                    break;
                case "retrait":
                    processRetrait(request, response, compte);
                    break;
                case "transfert":
                    processTransfert(request, response, compte);
                    break;
                default:
                    response.sendRedirect("compte?action=view&numero=" + numeroCompte);
                    break;
            }
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors du traitement de l'opération");
        }
    }

    private void processDepot(HttpServletRequest request, HttpServletResponse response, Compte compte)
            throws IOException, ServletException {
        try {
            Double montant = Double.parseDouble(request.getParameter("montant"));
            
            if (montant <= 0) {
                request.setAttribute("error", "Le montant doit être positif");
                request.setAttribute("compte", compte);
                request.getRequestDispatcher("/courant/operation-depot.jsp").forward(request, response);
                return;
            }

            compteService.deposit(compte, montant);
            response.sendRedirect("compte?action=view&numero=" + compte.getNumeroCompte() + "&success=depot&montant=" + montant);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du dépôt: " + e.getMessage());
            request.setAttribute("compte", compte);
            request.getRequestDispatcher("/courant/operation-depot.jsp").forward(request, response);
        }
    }

    private void processRetrait(HttpServletRequest request, HttpServletResponse response, Compte compte)
            throws IOException, ServletException {
        try {
            Double montant = Double.parseDouble(request.getParameter("montant"));
            
            if (montant <= 0) {
                request.setAttribute("error", "Le montant doit être positif");
                request.setAttribute("compte", compte);
                request.getRequestDispatcher("/courant/operation-retrait.jsp").forward(request, response);
                return;
            }

            boolean success = compteService.withdraw(compte, montant);
            if (success) {
                response.sendRedirect("compte?action=view&numero=" + compte.getNumeroCompte() + "&success=retrait&montant=" + montant);
            } else {
                request.setAttribute("error", "Fonds insuffisants pour effectuer ce retrait");
                request.setAttribute("compte", compte);
                request.getRequestDispatcher("/courant/operation-retrait.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du retrait: " + e.getMessage());
            request.setAttribute("compte", compte);
            request.getRequestDispatcher("/courant/operation-retrait.jsp").forward(request, response);
        }
    }

    private void processTransfert(HttpServletRequest request, HttpServletResponse response, Compte fromCompte)
            throws IOException, ServletException {
        try {
            String toNumeroCompte = request.getParameter("toNumeroCompte");
            Double montant = Double.parseDouble(request.getParameter("montant"));
            
            if (montant <= 0) {
                request.setAttribute("error", "Le montant doit être positif");
                request.setAttribute("compte", fromCompte);
                request.getRequestDispatcher("/courant/operation-transfert.jsp").forward(request, response);
                return;
            }

            Compte toCompte = compteService.findAccountByNumero(toNumeroCompte);
            if (toCompte == null) {
                request.setAttribute("error", "Compte destinataire non trouvé");
                request.setAttribute("compte", fromCompte);
                request.getRequestDispatcher("/courant/operation-transfert.jsp").forward(request, response);
                return;
            }

            if (fromCompte.getNumeroCompte().equals(toCompte.getNumeroCompte())) {
                request.setAttribute("error", "Impossible de transférer vers le même compte");
                request.setAttribute("compte", fromCompte);
                request.getRequestDispatcher("/courant/operation-transfert.jsp").forward(request, response);
                return;
            }

            compteService.transfer(fromCompte, toCompte, montant);
            response.sendRedirect("compte?action=view&numero=" + fromCompte.getNumeroCompte() + "&success=transfert&montant=" + montant + "&destinataire=" + toNumeroCompte);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("compte", fromCompte);
            request.getRequestDispatcher("/courant/operation-transfert.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du transfert: " + e.getMessage());
            request.setAttribute("compte", fromCompte);
            request.getRequestDispatcher("/courant/operation-transfert.jsp").forward(request, response);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e, String message)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("error", message + ": " + e.getMessage());
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}