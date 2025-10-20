package com.bank.central.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/diagnostic")
public class DiagnosticServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Diagnostic EJB</title></head>");
        out.println("<body>");
        out.println("<h1>Diagnostic EJB</h1>");
        
        try {
            InitialContext context = new InitialContext();
            
            out.println("<h2>JNDI Context:</h2>");
            out.println("<pre>");
            listContext(context, "java:", 0 , out);
            out.println("</pre>");
            
        } catch (Exception e) {
            out.println("<p style='color: red;'>Erreur: " + e.getMessage() + "</p>");
        }
        
        out.println("</body>");
        out.println("</html>");
    }
    
    private void listContext(Context ctx, String name, int depth , PrintWriter out) throws NamingException {

        
        String indent = "  ".repeat(depth);
        out.println(indent + name);
        
        try {
            Enumeration<javax.naming.NameClassPair> list = ctx.list(name);
            while (list.hasMoreElements()) {
                javax.naming.NameClassPair pair = list.nextElement();
                String className = pair.getClassName();
                String fullName = name + (name.endsWith(":") ? "" : "/") + pair.getName();
                
                out.println(indent + "└── " + pair.getName() + " (" + className + ")");
                
                // Explorer récursivement si c'est un contexte
                if (className.contains("Context")) {
                    try {
                        Object obj = ctx.lookup(fullName);
                        if (obj instanceof Context) {
                            listContext((Context) obj, fullName, depth + 1 , out);
                        }
                    } catch (Exception e) {
                        out.println(indent + "    └── ERROR: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            out.println(indent + "└── ERROR listing: " + e.getMessage());
        }
    }
}