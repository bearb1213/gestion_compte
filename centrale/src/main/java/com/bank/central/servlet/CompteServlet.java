package com.bank.central.servlet;

import jakarta.servlet.annotation.*;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.bank.courant.model.Compte;
import com.bank.courant.model.Particulie;
import com.bank.courant.remote.CompteServiceRemote;
import com.bank.courant.remote.ParticulieServiceRemote;

@WebServlet("/compte")
@MultipartConfig
public class CompteServlet extends HttpServlet {
    
    @EJB(lookup = "java:global/courant-1.0.0/CompteService!com.bank.courant.remote.CompteServiceRemote")
    private CompteServiceRemote compteService;
    
    @EJB(lookup = "java:global/courant-1.0.0/ParticulieService!com.bank.courant.remote.ParticulieServiceRemote")
    private ParticulieServiceRemote particulieService;

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
                case "view":
                    viewCompte(request, response);
                    break;
                case "block":
                    blockCompte(request, response);
                    break;
                case "unblock":
                    unblockCompte(request, response);
                    break;
                case "balance":
                    checkBalance(request, response);
                    break;
                default:
                    listComptes(request, response);
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
                case "create":
                    createCompte(request, response);
                    break;
                case "search":
                    searchCompte(request, response);
                    break;
                default:
                    listComptes(request, response);
                    break;
            }
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors du traitement du formulaire");
        }
    }

    private void listComptes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Dans une vraie application, vous auriez une méthode pour lister tous les comptes
            // Pour l'instant, on redirige vers la recherche
            RequestDispatcher dispatcher = request.getRequestDispatcher("/courant/compte-search.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération de la liste des comptes", e);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Particulie> particuliers = particulieService.findAll();
            request.setAttribute("particuliers", particuliers);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/courant/compte-form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement du formulaire", e);
        }
    }

    private void viewCompte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String numeroCompte = request.getParameter("numero");
            Compte compte = compteService.findAccountByNumero(numeroCompte);
            
            if (compte == null) {
                response.sendRedirect("compte?action=list&error=notfound");
                return;
            }
            
            Double solde = compteService.getBalance(compte);
            request.setAttribute("compte", compte);
            request.setAttribute("solde", solde);
            request.setAttribute("status", compteService.getStatus(compte));
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/courant/compte-view.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'affichage du compte", e);
        }
    }

    private void createCompte(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        try {
            int particulieId = Integer.parseInt(request.getParameter("particulieId"));
            Double montantDecouvert = Double.parseDouble(request.getParameter("montantDecouvert"));
            Double tenuCompte = Double.parseDouble(request.getParameter("tenuCompte"));
            Double fraisDecouvert = Double.parseDouble(request.getParameter("fraisDecouvert"));

            Particulie particulie = particulieService.find(particulieId);
            if (particulie == null) {
                request.setAttribute("error", "Particulier non trouvé");
                showNewForm(request, response);
                return;
            }

            Compte nouveauCompte = compteService.createAccount(particulie, montantDecouvert, tenuCompte, fraisDecouvert);
            
            response.sendRedirect("compte?action=view&numero=" + nouveauCompte.getNumeroCompte() + "&success=create");
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création du compte: " + e.getMessage());
            showNewForm(request, response);
        }
    }

    private void searchCompte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String numeroCompte = request.getParameter("numeroCompte");
            Compte compte = compteService.findAccountByNumero(numeroCompte);
            
            if (compte == null) {
                request.setAttribute("error", "Aucun compte trouvé avec le numéro: " + numeroCompte);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/courant/compte-search.jsp");
                dispatcher.forward(request, response);
                return;
            }
            
            Double solde = compteService.getBalance(compte);
            request.setAttribute("compte", compte);
            request.setAttribute("solde", solde);
            request.setAttribute("status", compteService.getStatus(compte));
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/courant/compte-view.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la recherche du compte", e);
        }
    }

    private void blockCompte(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            String numeroCompte = request.getParameter("numero");
            Compte compte = compteService.findAccountByNumero(numeroCompte);
            
            if (compte == null) {
                response.sendRedirect("compte?action=list&error=notfound");
                return;
            }
            
            compteService.blockAccount(compte);
            response.sendRedirect("compte?action=view&numero=" + numeroCompte + "&success=block");
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors du blocage du compte");
        }
    }

    private void unblockCompte(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            String numeroCompte = request.getParameter("numero");
            Compte compte = compteService.findAccountByNumero(numeroCompte);
            
            if (compte == null) {
                response.sendRedirect("compte?action=list&error=notfound");
                return;
            }
            
            compteService.unblockAccount(compte);
            response.sendRedirect("compte?action=view&numero=" + numeroCompte + "&success=unblock");
        } catch (Exception e) {
            handleException(request, response, e, "Erreur lors du déblocage du compte");
        }
    }

    private void checkBalance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String numeroCompte = request.getParameter("numero");
            String dateStr = request.getParameter("date");
            
            Compte compte = compteService.findAccountByNumero(numeroCompte);
            if (compte == null) {
                request.setAttribute("error", "Compte non trouvé");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/courant/compte-search.jsp");
                dispatcher.forward(request, response);
                return;
            }
            
            Double solde;
            if (dateStr != null && !dateStr.isEmpty()) {
                LocalDate date = LocalDate.parse(dateStr);
                solde = compteService.getBalance(compte, date);
                request.setAttribute("dateRecherche", date);
            } else {
                solde = compteService.getBalance(compte);
            }
            
            request.setAttribute("compte", compte);
            request.setAttribute("solde", solde);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/courant/compte-balance.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la consultation du solde", e);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e, String message)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("error", message + ": " + e.getMessage());
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}