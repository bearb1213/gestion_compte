package com.bank.central.servlet;
public class RemotingTestServlet {
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

// @WebServlet("/remoting-test")
// public class RemotingTestServlet extends HttpServlet {
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
        
//         response.setContentType("text/html");
//         PrintWriter out = response.getWriter();
        
//         String[] providerUrls = {
//             "http-remoting://localhost:8070",
//             "remote+http://localhost:8070", 
//             "http-remoting://localhost:8070?version=2",
//             "remote+http://localhost:4447"
//         };
        
//         for (String providerUrl : providerUrls) {
//             out.println("<h3>Test avec: " + providerUrl + "</h3>");
//             testConnection(out, providerUrl);
//         }
//     }
    
//     private void testConnection(PrintWriter out, String providerUrl) {
//         try {
//             Properties props = new Properties();
//             props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//             props.put(Context.PROVIDER_URL, providerUrl);
//             props.put(Context.SECURITY_PRINCIPAL, "admin");
//             props.put(Context.SECURITY_CREDENTIALS, "admin");
//             props.put("jboss.naming.client.ejb.context", true);
            
//             Context ctx = new InitialContext(props);
//             out.println("<p style='color:green'>✅ Contexte créé</p>");
            
//             String jndiName = "ejb:change/change/ChangeService!com.bank.change.remote.ChangeServiceRemote";
//             ChangeServiceRemote service = (ChangeServiceRemote) ctx.lookup(jndiName);
//             out.println("<p style='color:green'>✅ Lookup réussi</p>");
            
//             List<?> result = service.getAllChanges();
//             out.println("<p style='color:green'>✅ Appel EJB réussi: " + result.size() + " éléments</p>");
            
//             ctx.close();
            
//         } catch (Exception e) {
//             out.println("<p style='color:red'>❌ Erreur: " + e.getClass().getSimpleName() + " - " + e.getMessage() + "</p>");
//         }
//     }
// }