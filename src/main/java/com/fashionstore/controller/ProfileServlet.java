package com.fashionstore.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // 1. Security Check: Ensure user is logged in
        if (session == null || session.getAttribute("activeUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Forward to the Profile view
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}