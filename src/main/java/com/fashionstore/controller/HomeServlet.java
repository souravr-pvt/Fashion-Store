package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fashionstore.dao.ProductDao;
import com.fashionstore.dao.impl.ProductDaoImpl;
import com.fashionstore.model.Product;

// This maps the servlet to the "/home" URL path
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        // Initialize our Product DAO
        productDao = new ProductDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Fetch all active products from the database
        List<Product> products = productDao.getActiveProducts();
        
        // 2. Attach the list of products to the request object
        // We will use the name "productList" to access this inside our JSP
        request.setAttribute("productList", products);
        
        // 3. Forward the data and the user to the index.jsp page
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}