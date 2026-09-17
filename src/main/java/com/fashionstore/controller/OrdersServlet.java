package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fashionstore.dao.OrderDao;
import com.fashionstore.dao.impl.OrderDaoImpl;
import com.fashionstore.model.Order;
import com.fashionstore.model.User;

@WebServlet("/OrdersServlet")
public class OrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // 1. Security check: make sure they are logged in
        if (session == null || session.getAttribute("activeUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("activeUser");
        OrderDao orderDao = new OrderDaoImpl();
        
        // 2. Fetch the fresh order history from Database using your DAO
        List<Order> orderHistory = orderDao.getOrdersByUserId(user.getUserId());
        
        // 3. Attach it to the session so my_orders.jsp can read it
        session.setAttribute("orderHistory", orderHistory);
        
        // 4. Forward the user to the view page
        request.getRequestDispatcher("/my_orders.jsp").forward(request, response);
    }
}