package com.fashionstore.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fashionstore.dao.UserDao;
import com.fashionstore.dao.impl.UserDaoImpl;
import com.fashionstore.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Get the credentials from the login.jsp form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Pass credentials to the DAO to check against the database
        // (Note: Make sure your UserDao interface has this loginUser method defined)
        User loggedInUser = userDao.loginUser(email, password);

        // 3. Handle the login logic
        if (loggedInUser != null) {
            // Success! Create a session for this user
            HttpSession session = request.getSession();
            
            // Store the entire User object in the session. 
            // We can now access their name, ID, and cart anywhere in the app!
            session.setAttribute("activeUser", loggedInUser);
            
            System.out.println("User logged in successfully: " + loggedInUser.getFullName());
            
            // Redirect to the homepage (we will build this next)
            response.sendRedirect("index.jsp");
        } else {
            // Failure! Redirect back to login page with an error flag
            System.out.println("Failed login attempt for email: " + email);
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}