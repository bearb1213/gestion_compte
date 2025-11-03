package com.bank.central.servlet;
public class TestEJBClientLogsServlet {
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

// @WebServlet("/test-ejb")
// public class TestEJBClientLogsServlet extends HttpServlet {
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
        
//         response.setContentType("text/html");
//         PrintWriter out = response.getWriter();
        
//         // Activez les logs programmatiquement
//         System.setProperty("org.jboss.ejb.client.logging", "TRACE");
//         System.setProperty("org.jboss.remoting.logging", "TRACE");
//         System.setProperty("org.xnio.logging", "DEBUG");
        
//         out.println("<h2>Logs EJB Client activés</h2>");
        
//         try {
//             Properties props = new Properties();
//             props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//             props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8081");
//             props.put(Context.SECURITY_PRINCIPAL, "change");
//             props.put(Context.SECURITY_CREDENTIALS, "change");
            
//             out.println("<p>Configuration EJB Client appliquée</p>");
            
//             InitialContext ctx = new InitialContext(props);
//             out.println("<p style='color:green'>✅ Contexte InitialContext créé</p>");
            
//             String jndiName = "ejb:/change-1.0.0/ChangeService!com.bank.change.remote.ChangeServiceRemote";
//             ChangeServiceRemote service = (ChangeServiceRemote) ctx.lookup(jndiName);
//             out.println("<p style='color:green'>✅ Lookup JNDI réussi</p>");
            
//             // Force l'initialisation du client
//             out.println("<p>Initialisation du client EJB...</p>");
            
//             List<?> result = service.getAllChanges();
//             out.println("<p style='color:green'>✅ SUCCÈS: " + result.size() + " éléments</p>");
            
//             ctx.close();
            
//         } catch (Exception e) {
//             out.println("<p style='color:red'>❌ Erreur: " + e.getMessage() + "</p>");
//             out.println("<h3>Stack trace complète:</h3>");
//             e.printStackTrace(out);
//         }
//     }
// }