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

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Grab everything from the URL / Form
        String query = request.getParameter("query");
        String sort = request.getParameter("sort");
        
        // Convert prices from Strings to Doubles safely
        String minPriceStr = request.getParameter("minPrice");
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        
        String maxPriceStr = request.getParameter("maxPrice");
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;
        
        // Use getParameterValues for checkboxes since they can have multiple selections!
        String[] brands = request.getParameterValues("brand");
        String[] colors = request.getParameterValues("color");

        // 2. Pass it all to our new super-method
        ProductDao productDao = new ProductDaoImpl();
        List<Product> searchResults = productDao.filterProducts(query, sort, minPrice, maxPrice, brands, colors);
        
        // 3. Send the results and the search query back to the page
        request.setAttribute("searchQuery", query);
        request.setAttribute("productList", searchResults);
        
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}