package com.bank.change.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.ejb.EJB;
import jakarta.servlet.*;

import com.bank.change.remote.ChangeServiceRemote;
import com.bank.change.remote.ChangeService;

import com.bank.change.model.Change;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;


@WebServlet("/change")
public class ChangeServlet extends HttpServlet {
    
    @EJB
    private ChangeServiceRemote changeService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Change> changes = changeService.getAllChanges();

        // 3️⃣ Retourner la réponse JSON
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        for(Change change : changes) {
            out.println(change.toString());
        }

    }

    
}
