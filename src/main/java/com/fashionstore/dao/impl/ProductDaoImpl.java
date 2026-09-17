package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDao;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public class ProductDaoImpl implements ProductDao {

    @Override
    public boolean addProduct(Product product) {
        boolean isAdded = false;
        String query = "INSERT INTO products (category_id, product_name, description, discount_percentage, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, product.getCategoryId());
            pstmt.setString(2, product.getProductName());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getDiscountPercentage());
            pstmt.setString(5, product.getImageUrl());
            pstmt.setBoolean(6, product.isActive());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }
    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        // Use % around the keyword to find it anywhere in the name or description
        String sql = "SELECT * FROM products WHERE product_name LIKE ? OR description LIKE ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                // We will add brand and color to the model later!
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public List<Product> filterProducts(String keyword, String sort, Double minPrice, Double maxPrice, String[] brands, String[] colors) {
        List<Product> products = new ArrayList<>();
        
        // We start with a base query that selects everything
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        // 1. Add Keyword Search
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (product_name LIKE ? OR description LIKE ?)");
            parameters.add("%" + keyword + "%");
            parameters.add("%" + keyword + "%");
        }

        // 2. Add Price Range
        if (minPrice != null) {
            sql.append(" AND price >= ?");
            parameters.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND price <= ?");
            parameters.add(maxPrice);
        }

        // 3. Add Brands (e.g., AND brand IN ('Nike', 'Levis'))
        if (brands != null && brands.length > 0) {
            sql.append(" AND brand IN (");
            for (int i = 0; i < brands.length; i++) {
                sql.append("?");
                if (i < brands.length - 1) sql.append(",");
                parameters.add(brands[i]);
            }
            sql.append(")");
        }

        // 4. Add Colors
        if (colors != null && colors.length > 0) {
            sql.append(" AND color IN (");
            for (int i = 0; i < colors.length; i++) {
                sql.append("?");
                if (i < colors.length - 1) sql.append(",");
                parameters.add(colors[i]);
            }
            sql.append(")");
        }

        // 5. Add Sorting
        if ("price_asc".equals(sort)) {
            sql.append(" ORDER BY price ASC");
        } else if ("price_desc".equals(sort)) {
            sql.append(" ORDER BY price DESC");
        } else if ("newest".equals(sort)) {
            sql.append(" ORDER BY product_id DESC");
        }

        // 6. Execute the final built query
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
             
            // Safely map all our variables into the PreparedStatement
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                products.add(product);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean isUpdated = false;
        String query = "UPDATE products SET category_id = ?, product_name = ?, description = ?, discount_percentage = ?, image_url = ?, is_active = ? WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, product.getCategoryId());
            pstmt.setString(2, product.getProductName());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getDiscountPercentage());
            pstmt.setString(5, product.getImageUrl());
            pstmt.setBoolean(6, product.isActive());
            pstmt.setInt(7, product.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteProduct(int productId) {
        boolean isDeleted = false;
        String query = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public Product getProductById(int productId) {
        Product product = null;
        String query = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = extractProductFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getActiveProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE is_active = true";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, categoryId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(extractProductFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Helper method to keep code DRY
    private Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        // FIXED: Changed setProductId to setId to match the Product.java model
        product.setId(rs.getInt("product_id"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setProductName(rs.getString("product_name"));
        product.setDescription(rs.getString("description"));
        product.setDiscountPercentage(rs.getDouble("discount_percentage"));
        product.setImageUrl(rs.getString("image_url"));
        product.setActive(rs.getBoolean("is_active"));
        
        // Added these so your prices actually show up on the frontend!
        try {
            product.setPrice(rs.getDouble("price"));
            product.setStock(rs.getInt("stock"));
        } catch (SQLException e) {
            // Fails silently if 'price' or 'stock' columns don't exist in your DB yet, preventing crashes.
        }
        
        return product;
    }
}