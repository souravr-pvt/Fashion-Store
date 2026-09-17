package com.fashionstore.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the current session, but don't create a new one if it doesn't exist (false)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // This destroys the session and removes the "activeUser" attribute
            session.invalidate();
            System.out.println("User logged out successfully.");
        }
        
        // Redirect back to the homepage Servlet
        response.sendRedirect("home");
    }
}