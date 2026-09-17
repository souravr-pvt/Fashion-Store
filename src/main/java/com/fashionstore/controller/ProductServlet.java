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

// Mapping this to "/products" for a clean URL
@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Fetch all products (you could also use getActiveProducts here)
        List<Product> products = productDao.getAllProducts();
        
        // 2. Attach the list to the request so JSTL can loop through it
        request.setAttribute("productList", products);
        
        // 3. Forward to the view
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }
}