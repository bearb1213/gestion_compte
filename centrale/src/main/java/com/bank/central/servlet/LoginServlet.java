package com.bank.central.servlet;

import com.bank.courant.model.Direction;
import com.bank.courant.model.Utilisateur;
import com.bank.courant.remote.ActionRoleRemote;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    @EJB(lookup = "java:global/courant-1.0.0/ActionRoleService!com.bank.courant.remote.ActionRoleRemote")
    private ActionRoleRemote roleService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if ("/logout".equals(path)) {
            // Déconnexion
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            System.out.println("\n\n\n\n\n Deconnexion \n\n\n\n");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            System.out.println("\n\n\n\n\n Login Get \n\n\n\n");
            // Redirection vers la page de login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
                System.out.println("\n\n\n\n\n Champs vides \n\n\n\n");
            
            request.setAttribute("errorMessage", "Veuillez remplir tous les champs");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        try {
            // Vérifier si l'utilisateur existe
            Utilisateur utilisateur = roleService.authenticate(username, password);
            if (utilisateur != null) {
                // Authentification réussie
                
                roleService.charge();
                
                
                
                // Créer la session
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                session.setAttribute("username", utilisateur.getUsername());
                session.setAttribute("idDirection", utilisateur.getDirection().getId());
                
                System.out.println("n\n\n\n\n Creation d'utilisateur \n\n\n\n");
                // Rediriger vers la page d'accueil
                response.sendRedirect(request.getContextPath() + "/accueil.jsp");
                
            } else {
                System.out.println("\n\n\n\n\n Authentification echouee \n\n\n\n");
                // Authentification échouée
                request.setAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect");
                request.setAttribute("username", username);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n Exception lors de l'authentification \n\n\n\n");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'authentification: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}