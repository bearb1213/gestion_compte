package com.bank.central.servlet.pret;

import com.bank.pret.remote.PretServiceRemote;
import com.bank.courant.remote.ParticulieServiceRemote;
import com.bank.pret.model.Compte;
import com.bank.pret.model.Pret;
import com.bank.pret.model.Status;
import com.bank.courant.model.Particulie;
import com.bank.pret.util.RemboursementVisualisation;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DashboardPretServlet", value = "/dashboardPret")
public class DashboardPretServlet extends HttpServlet {
    
    @EJB(lookup = "java:global/pret-1.0.0/PretService!com.bank.pret.remote.PretServiceRemote")
    private PretServiceRemote pretService;
    
    @EJB(lookup = "java:global/courant-1.0.0/ParticulieService!com.bank.courant.remote.ParticulieServiceRemote")
    private ParticulieServiceRemote particulieService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "view";
        }
        
        try {
            switch (action) {
                case "createAccount":
                    showCreateAccountForm(request, response);
                    break;
                case "viewAccounts":
                    viewAllAccounts(request, response);
                    break;
                case "applyLoan":
                    showApplyLoanForm(request, response);
                    break;
                case "viewSchedule":
                    showViewScheduleForm(request, response);
                    break;
                case "listParticuliers":
                    listParticuliers(request, response);
                    break;
                case "blockAccount":
                    blockAccount(request, response);
                    break;
                case "unblockAccount":
                    unblockAccount(request, response);
                    break;
                default:
                    showDashboard(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("/pret/error.jsp").forward(request, response);
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pret/dashboard.jsp").forward(request, response);
    }
    
    private void showCreateAccountForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Particulie> particuliers = particulieService.findAll();
        request.setAttribute("particuliers", particuliers);
        request.getRequestDispatcher("/pret/createAccount.jsp").forward(request, response);
    }
    
    private void viewAllAccounts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Compte> comptes = pretService.findAllAccounts();
        request.setAttribute("comptes", comptes);
        request.getRequestDispatcher("/pret/viewAccounts.jsp").forward(request, response);
    }
    
    private void showApplyLoanForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Compte> comptes = pretService.findAllAccounts();
        request.setAttribute("comptes", comptes);
        request.getRequestDispatcher("/pret/applyLoan.jsp").forward(request, response);
    }
    
    private void showViewScheduleForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pret/viewSchedule.jsp").forward(request, response);
    }
    
    private void listParticuliers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Particulie> particuliers = particulieService.findAll();
        request.setAttribute("particuliers", particuliers);
        request.getRequestDispatcher("/pret/listParticuliers.jsp").forward(request, response);
    }
    
    private void blockAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroCompte = request.getParameter("numeroCompte");
        Compte compte = pretService.findAccountByNumero(numeroCompte);
        if (compte != null) {
            pretService.blockAccount(compte);
            request.setAttribute("success", "Compte " + numeroCompte + " bloqué avec succès");
        } else {
            request.setAttribute("error", "Compte non trouvé: " + numeroCompte);
        }
        viewAllAccounts(request, response);
    }
    
    private void unblockAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroCompte = request.getParameter("numeroCompte");
        Compte compte = pretService.findAccountByNumero(numeroCompte);
        if (compte != null) {
            pretService.unblockAccount(compte);
            request.setAttribute("success", "Compte " + numeroCompte + " débloqué avec succès");
        } else {
            request.setAttribute("error", "Compte non trouvé: " + numeroCompte);
        }
        viewAllAccounts(request, response);
    }
}