package com.bank.central.servlet;
public class TestLocalEJBServlet {
}
// package com.bank.central.servlet;

// import jakarta.servlet.http.*;
// import jakarta.servlet.*;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.Properties;
// import javax.naming.Context;
// import javax.naming.InitialContext;
// import jakarta.servlet.annotation.WebServlet;
// import com.bank.change.remote.ChangeServiceRemote;
// import java.util.List;
// import javax.naming.NamingException;


// @WebServlet("/test-local-ejb")
// public class TestLocalEJBServlet extends HttpServlet {
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
        
//         response.setContentType("text/html");
//         PrintWriter out = response.getWriter();
        
//         Context ctx = null;
//         try {
//             // Configuration plus complète
//             Properties props = new Properties();
//             props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//             props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8081");
//             props.put(Context.SECURITY_PRINCIPAL, "change"); // Si sécurité activée
//             props.put(Context.SECURITY_CREDENTIALS, "change"); // Si sécurité activée
//             // props.put("jboss.naming.client.ejb.context", true);
//             // props.put("org.jboss.ejb.client.scoped.context", true);
            
//             ctx = new InitialContext(props);
//             out.println("<p style='color:green'>✅ Contexte créé avec succès</p>");
            
//             // Test avec différents formats JNDI
//             String[] jndiNames = {
//                 "ejb:/change-1.0.0/ChangeService!com.bank.change.remote.ChangeServiceRemote",
//                 "java:global/change-1.0.0/ChangeService!com.bank.change.remote.ChangeServiceRemote",
//                 "java:/change-1.0.0/ChangeService!com.bank.change.remote.ChangeServiceRemote"
//             };
            
//             ChangeServiceRemote service = null;
//             String successfulJndi = null;
            
//             for (String jndiName : jndiNames) {
//                 try {
//                     out.println("<p>Tentative avec JNDI: " + jndiName + "</p>");
//                     service = (ChangeServiceRemote) ctx.lookup(jndiName);
//                     successfulJndi = jndiName;
//                     out.println("<p style='color:green'>✅ Lookup réussi avec: " + jndiName + "</p>");
//                     out.println("<p>taille du Change : "+service.getAllChanges().size()+"</p>");


//                 } catch (Exception e) {
//                     out.println("<p style='color:orange'>⚠ Échec avec: " + jndiName + " - " + e.getMessage() + "</p>");
//                 }
//             }
            
//             if (service != null) {
//                 List<?> result = service.getAllChanges();
//                 out.println("<p style='color:green'>✅ SUCCÈS! Appel EJB réussi: " + result.size() + " éléments</p>");
//             } else {
//                 out.println("<p style='color:red'>❌ Tous les lookups JNDI ont échoué</p>");
//             }
            
//         } catch (Exception e) {
//             out.println("<p style='color:red'>❌ Erreur: " + e.getMessage() + "</p>");
//             e.printStackTrace();
//         } finally {
//             if (ctx != null) {
//                 try {
//                     ctx.close();
//                 } catch (NamingException e) {
//                     // Ignorer
//                 }
//             }
//         }
//     }
// }