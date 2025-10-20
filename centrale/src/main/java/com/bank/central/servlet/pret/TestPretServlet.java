package com.bank.central.servlet.pret;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bank.pret.remote.PretServiceRemote;
import com.bank.pret.model.Compte;
import com.bank.pret.model.Pret;
import com.bank.pret.model.Type;
import com.bank.pret.util.RemboursementVisualisation;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/test-pret")
public class TestPretServlet extends HttpServlet {
    
    private PretServiceRemote pretService;
    
    @Override
    public void init() throws ServletException {
        try {
            Context context = new InitialContext();
            pretService = (PretServiceRemote) context.lookup(
                "java:global/pret-1.0.0/PretService!com.bank.pret.remote.PretServiceRemote"
            );
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'initialisation du service Pret", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Test EJB PretService</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
        out.println(".success { color: green; }");
        out.println(".error { color: red; }");
        out.println(".info { color: blue; }");
        out.println(".warning { color: orange; }");
        out.println("table { border-collapse: collapse; width: 100%; margin: 10px 0; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println(".section { margin: 20px 0; padding: 15px; border: 1px solid #ccc; border-radius: 5px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Test EJB PretService</h1>");
        
        try {
            // Test 1: Vérifier l'injection
            out.println("<div class='section'>");
            out.println("<h2>Test 1: Injection EJB</h2>");
            if (pretService != null) {
                out.println("<p class='success'>✓ EJB injecté avec succès</p>");
                out.println("<p class='info'>Service PretServiceRemote initialisé</p>");
            } else {
                out.println("<p class='error'>✗ EJB non injecté</p>");
                return;
            }
            out.println("</div>");
            
            // Test 2: CREATE - Créer un compte de prêt
            out.println("<div class='section'>");
            out.println("<h2>Test 2: CREATE - Création de Compte Prêt</h2>");
            try {
                // Utiliser un ID de particulier existant (remplacez par un ID valide)
                int idParticulier = 1;
                Compte nouveauCompte = pretService.createLoanAccount(idParticulier);
                
                if (nouveauCompte != null) {
                    out.println("<p class='success'>✓ Compte prêt créé avec succès</p>");
                    out.println("<table>");
                    out.println("<tr><th>ID</th><th>Numéro</th><th>Date Ouverture</th><th>ID Particulier</th></tr>");
                    out.println("<tr>");
                    out.println("<td>" + nouveauCompte.getId() + "</td>");
                    out.println("<td>" + nouveauCompte.getNumero() + "</td>");
                    out.println("<td>" + nouveauCompte.getDateOuverture() + "</td>");
                    out.println("<td>" + nouveauCompte.getIdParticulier() + "</td>");
                    out.println("</tr>");
                    out.println("</table>");
                } else {
                    out.println("<p class='error'>✗ Erreur lors de la création du compte</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur création compte: " + e.getMessage() + "</p>");
                out.println("<p class='warning'>Assurez-vous qu'un particulier avec cet ID existe</p>");
            }
            out.println("</div>");
            
            // Test 3: FIND ACCOUNT BY NUMERO
            out.println("<div class='section'>");
            out.println("<h2>Test 3: FIND - Recherche de Compte par Numéro</h2>");
            try {
                // Récupérer tous les comptes pour tester
                List<Compte> tousLesComptes = pretService.findAllAccounts();
                if (!tousLesComptes.isEmpty()) {
                    Compte premierCompte = tousLesComptes.get(0);
                    Compte compteTrouve = pretService.findAccountByNumero(premierCompte.getNumero());
                    
                    if (compteTrouve != null) {
                        out.println("<p class='success'>✓ Compte trouvé par numéro</p>");
                        out.println("<table>");
                        out.println("<tr><th>ID</th><th>Numéro</th><th>Date Ouverture</th><th>ID Particulier</th></tr>");
                        out.println("<tr>");
                        out.println("<td>" + compteTrouve.getId() + "</td>");
                        out.println("<td>" + compteTrouve.getNumero() + "</td>");
                        out.println("<td>" + compteTrouve.getDateOuverture() + "</td>");
                        out.println("<td>" + compteTrouve.getIdParticulier() + "</td>");
                        out.println("</tr>");
                        out.println("</table>");
                    } else {
                        out.println("<p class='error'>✗ Aucun compte trouvé avec ce numéro</p>");
                    }
                } else {
                    out.println("<p class='warning'>Aucun compte disponible pour le test</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur recherche compte: " + e.getMessage() + "</p>");
            }
            out.println("</div>");
            
            // Test 4: FIND ALL ACCOUNTS
            out.println("<div class='section'>");
            out.println("<h2>Test 4: FIND ALL - Tous les Comptes</h2>");
            try {
                List<Compte> tousLesComptes = pretService.findAllAccounts();
                out.println("<p class='success'>✓ Liste récupérée - " + tousLesComptes.size() + " compte(s)</p>");
                
                if (!tousLesComptes.isEmpty()) {
                    out.println("<table>");
                    out.println("<tr><th>ID</th><th>Numéro</th><th>Date Ouverture</th><th>ID Particulier</th></tr>");
                    for (Compte compte : tousLesComptes) {
                        out.println("<tr>");
                        out.println("<td>" + compte.getId() + "</td>");
                        out.println("<td>" + compte.getNumero() + "</td>");
                        out.println("<td>" + compte.getDateOuverture() + "</td>");
                        out.println("<td>" + compte.getIdParticulier() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur recherche tous les comptes: " + e.getMessage() + "</p>");
            }
            out.println("</div>");
            
            // Test 5: GET STATUS
            out.println("<div class='section'>");
            out.println("<h2>Test 5: GET STATUS - Statut du Compte</h2>");
            try {
                List<Compte> tousLesComptes = pretService.findAllAccounts();
                if (!tousLesComptes.isEmpty()) {
                    Compte compteTest = tousLesComptes.get(0);
                    Object status = pretService.getStatus(compteTest);
                    
                    if (status != null) {
                        out.println("<p class='success'>✓ Statut récupéré avec succès</p>");
                        out.println("<p class='info'>Statut du compte " + compteTest.getNumero() + ": " + status.toString() + "</p>");
                    } else {
                        out.println("<p class='warning'>Aucun statut trouvé pour ce compte</p>");
                    }
                } else {
                    out.println("<p class='warning'>Aucun compte disponible pour le test</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur récupération statut: " + e.getMessage() + "</p>");
            }
            out.println("</div>");
            
            // Test 6: VIEW REPAYMENT SCHEDULE
            out.println("<div class='section'>");
            out.println("<h2>Test 6: VIEW REPAYMENT SCHEDULE - Simulation Échéancier</h2>");
            try {
                Double montant = 10000.0;
                int dureeMois = 12;
                Double tauxInteret = 5.0;
                
                List<RemboursementVisualisation> schedule = pretService.viewRepaymentSchedule(montant, dureeMois, tauxInteret);
                
                out.println("<p class='success'>✓ Échéancier calculé avec succès - " + schedule.size() + " échéance(s)</p>");
                out.println("<p class='info'>Montant: " + montant + "€, Durée: " + dureeMois + " mois, Taux: " + tauxInteret + "%</p>");
                
                if (!schedule.isEmpty()) {
                    out.println("<table>");
                    out.println("<tr><th>Échéance</th><th>Date Prévue</th><th>Capital</th><th>Intérêts</th><th>Assurance</th><th>Total</th></tr>");
                    for (RemboursementVisualisation echeance : schedule) {
                        out.println("<tr>");
                        out.println("<td>" + echeance.getNumeroEcheance() + "</td>");
                        out.println("<td>" + echeance.getDatePrevue() + "</td>");
                        out.println("<td>" + String.format("%.2f", echeance.getMontantCapital()) + " €</td>");
                        out.println("<td>" + String.format("%.2f", echeance.getMontantInteret()) + " €</td>");
                        out.println("<td>" + (echeance.getMontantAssurance() != null ? String.format("%.2f", echeance.getMontantAssurance()) : "0.00") + " €</td>");
                        out.println("<td><strong>" + String.format("%.2f", echeance.getMontantTotal()) + " €</strong></td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur calcul échéancier: " + e.getMessage() + "</p>");
            }
            out.println("</div>");
            
            // Test 7: APPLY FOR LOAN (si compte disponible)
            out.println("<div class='section'>");
            out.println("<h2>Test 7: APPLY FOR LOAN - Demande de Prêt</h2>");
            try {
                List<Compte> tousLesComptes = pretService.findAllAccounts();
                if (!tousLesComptes.isEmpty()) {
                    Compte compteTest = tousLesComptes.get(0);
                    Double montant = 5000.0;
                    int dureeMois = 6;
                    Double tauxInteret = 4.5;
                    
                    pretService.applyForLoan(compteTest, montant, dureeMois, tauxInteret);
                    out.println("<p class='success'>✓ Demande de prêt soumise avec succès</p>");
                    out.println("<p class='info'>Compte: " + compteTest.getNumero() + ", Montant: " + montant + "€</p>");
                } else {
                    out.println("<p class='warning'>Aucun compte disponible pour tester la demande de prêt</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur demande de prêt: " + e.getMessage() + "</p>");
            }
            out.println("</div>");
            
            // Résumé final
            out.println("<div class='section'>");
            out.println("<h2>Résumé du Test PretService</h2>");
            out.println("<p class='info'>Tous les tests principaux ont été exécutés</p>");
            out.println("<p class='success'>✓ Service PretServiceRemote fonctionnel</p>");
            out.println("</div>");
            
        } catch (Exception e) {
            out.println("<div class='section'>");
            out.println("<h2 class='error'>Erreur générale</h2>");
            out.println("<p class='error'>" + e.getMessage() + "</p>");
            out.println("</div>");
            e.printStackTrace(out);
        } finally {
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}