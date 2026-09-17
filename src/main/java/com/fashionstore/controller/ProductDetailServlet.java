package com.fashionstore.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fashionstore.dao.ProductDao;
import com.fashionstore.dao.impl.ProductDaoImpl;
import com.fashionstore.model.Product;

@WebServlet("/ProductDetailServlet")
public class ProductDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int productId = Integer.parseInt(idParam);
                Product product = productDao.getProductById(productId);
                
                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/product_detail.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        // If product doesn't exist, send them back to the shop
        response.sendRedirect(request.getContextPath() + "/SearchServlet");
    }
}