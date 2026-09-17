package com.fashionstore.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fashionstore.dao.UserDao;
import com.fashionstore.dao.impl.UserDaoImpl;
import com.fashionstore.model.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        if (userDao.isEmailExist(email)) {
            response.sendRedirect("register.jsp?error=email_exists");
            return;
        }

        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setGender(gender);
        newUser.setAddress(address);
        newUser.setPassword(password); 

        boolean isRegistered = userDao.registerUser(newUser);

        if (isRegistered) {
            response.sendRedirect("login.jsp?success=registered");
        } else {
            response.sendRedirect("register.jsp?error=db_error");
        }
    }
}