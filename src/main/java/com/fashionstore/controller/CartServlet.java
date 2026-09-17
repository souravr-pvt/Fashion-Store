package com.fashionstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fashionstore.dao.ProductDao;
import com.fashionstore.dao.impl.ProductDaoImpl;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Product;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String productIdStr = request.getParameter("productId");

        if (productIdStr != null && action != null) {
            int productId = Integer.parseInt(productIdStr);
            HttpSession session = request.getSession();
            
            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            
            if (cart == null) {
                cart = new ArrayList<>();
            }

            if ("add".equals(action)) {
                ProductDao productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                
                String size = request.getParameter("size");
                if (size == null) {
                    size = "M"; 
                }
                
                if (product != null) {
                    boolean itemExists = false;
                    for (CartItem item : cart) {
                        // Check if exact product AND size is already in cart
                        if (item.getProductId() == productId && item.getSizeLabel().equals(size)) {
                            item.setQuantity(item.getQuantity() + 1);
                            itemExists = true;
                            break;
                        }
                    }
                    if (!itemExists) {
                        // Add new cart item
                        CartItem newItem = new CartItem(0, productId, size, 1, product.getPrice());
                        // IMPORTANT: Optional, but if your CartItem model accepts the product name/image, set them here so cart.jsp can display them!
                        // newItem.setProductName(product.getProductName()); 
                        // newItem.setImageUrl(product.getImageUrl());
                        cart.add(newItem);
                    }
                }
            } 
            else if ("increase".equals(action)) {
                for (CartItem item : cart) {
                    if (item.getProductId() == productId) {
                        item.setQuantity(item.getQuantity() + 1);
                        break;
                    }
                }
            } 
            else if ("decrease".equals(action)) {
                for (CartItem item : cart) {
                    if (item.getProductId() == productId) {
                        item.setQuantity(item.getQuantity() - 1);
                        break;
                    }
                }
                // Remove if quantity drops to 0
                cart.removeIf(item -> item.getQuantity() <= 0);
            } 
            else if ("remove".equals(action)) {
                cart.removeIf(item -> item.getProductId() == productId);
            }

            // Update the session
            session.setAttribute("cart", cart);

            // --- THE AJAX FIX ---
            // If JavaScript sent this request from the homepage/products page, reply "success" and stop.
            String isAjax = request.getParameter("ajax");
            if ("true".equals(isAjax)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8"); // Good practice to prevent character errors
                response.getWriter().write("success");
                return; 
            }

            // --- STANDARD REDIRECT ---
            // If it was a normal click (like from inside the cart.jsp itself), redirect normally.
            String referer = request.getHeader("Referer");
            if (referer != null) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart.jsp");
            }
        }
    }
}