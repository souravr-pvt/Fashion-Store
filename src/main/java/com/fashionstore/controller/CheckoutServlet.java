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
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.User;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // 1. Security Check
        if (session == null || session.getAttribute("activeUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("activeUser");
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // Prevent checkout if cart is empty
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        // 2. Fetch Form Data 
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String pincode = request.getParameter("pincode");
        String paymentMode = request.getParameter("paymentMode");
        String fullShippingAddress = address + ", " + city + " - " + pincode;

        // 3. Calculate Total Amount
        double totalAmount = 0;
        for (CartItem item : cart) {
            totalAmount += (item.getUnitPrice() * item.getQuantity());
        }

        // 4. Build the Order Object (FIXED METHOD NAMES HERE!)
        Order order = new Order();
        
        // NOTE: Make sure your User class actually uses getId() or getUserId() and match it here!
        order.setUserId(user.getUserId()); 
        
        order.setTotalAmount(totalAmount);
        order.setDeliveryAddress(fullShippingAddress); // Fixed to match Order.java
        order.setPaymentMethod(paymentMode);           // Fixed to match Order.java
        order.setOrderStatus("Completed");             // Fixed to match Order.java
        
        // Now this will work because we added setItems to Order.java!
        order.setItems(cart); 

        // 5. Save to MySQL via DAO
        OrderDao orderDao = new OrderDaoImpl();
        int generatedOrderId = orderDao.createOrder(order);

        // 6. Clear the Cart after successful checkout
        session.removeAttribute("cart");

        // 7. Update the session with fresh order history so it appears immediately
        List<Order> history = orderDao.getOrdersByUserId(user.getUserId()); // Match with the ID getter you used above
        session.setAttribute("orderHistory", history);

        // 8. Redirect to Success Page 
        response.sendRedirect(request.getContextPath() + "/order_success.jsp?orderId=" + generatedOrderId);
    }
}