package com.bank.central.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter {
}

// @WebFilter("/*")
// public class AuthenticationFilter implements Filter {

//     @Override
//     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//             throws IOException, ServletException {
        
//         HttpServletRequest httpRequest = (HttpServletRequest) request;
//         HttpServletResponse httpResponse = (HttpServletResponse) response;
        
//         String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
//         // URLs autorisées sans authentification
//         if (path.equals("/login.jsp") || path.equals("/index.jsp") || path.equals("/login") || 
//             path.startsWith("/css/") || path.startsWith("/js/") ) {
//             chain.doFilter(request, response);
//             System.out.println("\n\n\n\n\n Acces sans session \n\n\n\n");
//             return;
//         }
        
//         HttpSession session = httpRequest.getSession();
//         if (session == null || session.getAttribute("utilisateur") == null) {
//             System.out.println("\n\n\n\n\n Redirection vers login car seesion null \n\n\n\n");
//             httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
//         } else {
//             System.out.println("\n\n\n\n\n Session existante \n\n\n\n");
//             chain.doFilter(request, response);
//         }   
//     }
// }