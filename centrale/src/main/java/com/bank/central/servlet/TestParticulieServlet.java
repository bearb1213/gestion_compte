package com.bank.central.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ejb.EJB;

import com.bank.courant.remote.ParticulieServiceRemote;
import com.bank.courant.model.Particulie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.time.LocalDate;

@WebServlet("/test-particulie")
public class TestParticulieServlet extends HttpServlet {
    
    // Injection de l'EJB
    @EJB(lookup = "java:global/courant-1.0.0/ParticulieService!com.bank.courant.remote.ParticulieServiceRemote")
    private ParticulieServiceRemote particulieService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Test EJB ParticulieService</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
        out.println(".success { color: green; }");
        out.println(".error { color: red; }");
        out.println(".info { color: blue; }");
        out.println("table { border-collapse: collapse; width: 100%; margin: 10px 0; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Test EJB ParticulieService</h1>");
        
        try {
            // Test 1: Vérifier l'injection
            out.println("<h2>Test 1: Injection EJB</h2>");
            if (particulieService != null) {
                out.println("<p class='success'>✓ EJB injecté avec succès</p>");
            } else {
                out.println("<p class='error'>✗ EJB non injecté</p>");
                return;
            }
            
            // Test 2: CREATE - Créer un nouveau Particulie
            out.println("<h2>Test 2: CREATE</h2>");
            Particulie nouveauParticulie = new Particulie();
            nouveauParticulie.setCin("EE123456");
            nouveauParticulie.setNom("Test");
            nouveauParticulie.setPrenom("Utilisateur");
            nouveauParticulie.setEmail("test@bank.com");
            nouveauParticulie.setTelephone("0612345678");
            nouveauParticulie.setAdresse("123 Rue Test, Ville");
            nouveauParticulie.setDateNaissance(LocalDate.of(1990, 12, 11));
            
            try {
                particulieService.create(nouveauParticulie);
                out.println("<p class='success'>✓ Particulie créé avec succès</p>");
                out.println("<p class='info'>CIN: " + nouveauParticulie.getCin() + "</p>");
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur création: " + e.getMessage() + "</p>");
            }
            
            // Test 3: READ by CIN
            out.println("<h2>Test 3: FIND BY CIN</h2>");
            try {
                Particulie trouveParCin = particulieService.findByCin("EE123456");
                if (trouveParCin != null) {
                    out.println("<p class='success'>✓ Particulie trouvé par CIN</p>");
                    out.println("<table>");
                    out.println("<tr><th>ID</th><th>CIN</th><th>Nom</th><th>Prénom</th><th>Email</th></tr>");
                    out.println("<tr>");
                    out.println("<td>" + trouveParCin.getId() + "</td>");
                    out.println("<td>" + trouveParCin.getCin() + "</td>");
                    out.println("<td>" + trouveParCin.getNom() + "</td>");
                    out.println("<td>" + trouveParCin.getPrenom() + "</td>");
                    out.println("<td>" + trouveParCin.getEmail() + "</td>");
                    out.println("</tr>");
                    out.println("</table>");
                } else {
                    out.println("<p class='error'>✗ Aucun Particulie trouvé avec ce CIN</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur recherche par CIN: " + e.getMessage() + "</p>");
            }
            
            // Test 4: READ ALL
            out.println("<h2>Test 4: FIND ALL</h2>");
            try {
                List<Particulie> tousLesParticulies = particulieService.findAll();
                out.println("<p class='success'>✓ Liste récupérée - " + tousLesParticulies.size() + " élément(s)</p>");
                
                if (!tousLesParticulies.isEmpty()) {
                    out.println("<table>");
                    out.println("<tr><th>ID</th><th>CIN</th><th>Nom</th><th>Prénom</th><th>Email</th><th>Téléphone</th></tr>");
                    for (Particulie p : tousLesParticulies) {
                        out.println("<tr>");
                        out.println("<td>" + p.getId() + "</td>");
                        out.println("<td>" + p.getCin() + "</td>");
                        out.println("<td>" + p.getNom() + "</td>");
                        out.println("<td>" + p.getPrenom() + "</td>");
                        out.println("<td>" + p.getEmail() + "</td>");
                        out.println("<td>" + p.getTelephone() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur recherche tous: " + e.getMessage() + "</p>");
            }
            
            // Test 5: UPDATE
            out.println("<h2>Test 5: UPDATE</h2>");
            try {
                Particulie aModifier = particulieService.findByCin("EE123456");
                if (aModifier != null) {
                    String ancienEmail = aModifier.getEmail();
                    aModifier.setEmail("modifie@bank.com");
                    aModifier.setTelephone("0698765432");
                    
                    Particulie modifie = particulieService.update(aModifier);
                    out.println("<p class='success'>✓ Particulie modifié avec succès</p>");
                    out.println("<p class='info'>Ancien email: " + ancienEmail + " → Nouvel email: " + modifie.getEmail() + "</p>");
                } else {
                    out.println("<p class='error'>✗ Aucun Particulie à modifier trouvé</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur modification: " + e.getMessage() + "</p>");
            }
            
            // Test 6: DELETE
            out.println("<h2>Test 6: DELETE</h2>");
            try {
                Particulie aSupprimer = particulieService.findByCin("EE123456");
                if (aSupprimer != null) {
                    particulieService.delete(aSupprimer.getId());
                    out.println("<p class='success'>✓ Particulie supprimé avec succès</p>");
                    
                    // Vérifier la suppression
                    Particulie verif = particulieService.findByCin("EE123456");
                    if (verif == null) {
                        out.println("<p class='success'>✓ Suppression confirmée</p>");
                    } else {
                        out.println("<p class='error'>✗ Suppression non effective</p>");
                    }
                } else {
                    out.println("<p class='error'>✗ Aucun Particulie à supprimer trouvé</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>✗ Erreur suppression: " + e.getMessage() + "</p>");
            }
            
            // Résumé final
            out.println("<h2>Résumé du Test</h2>");
            out.println("<p class='info'>Tous les tests CRUD ont été exécutés</p>");
            
        } catch (Exception e) {
            out.println("<h2 class='error'>Erreur générale</h2>");
            out.println("<p class='error'>" + e.getMessage() + "</p>");
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