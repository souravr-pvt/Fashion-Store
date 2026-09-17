package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductSizeDao;
import com.fashionstore.model.ProductSize;
import com.fashionstore.util.DBConnection;

public class ProductSizeDaoImpl implements ProductSizeDao {

    @Override
    public boolean addProductSize(ProductSize productSize) {
        boolean isAdded = false;
        String query = "INSERT INTO product_sizes (product_id, size_label, stock_quantity, is_available, sku_code) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productSize.getProductId());
            pstmt.setString(2, productSize.getSizeLabel());
            pstmt.setInt(3, productSize.getStockQuantity());
            pstmt.setBoolean(4, productSize.isAvailable());
            pstmt.setString(5, productSize.getSkuCode());

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
    public boolean updateProductSize(ProductSize productSize) {
        boolean isUpdated = false;
        String query = "UPDATE product_sizes SET product_id = ?, size_label = ?, stock_quantity = ?, is_available = ?, sku_code = ? WHERE product_size_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productSize.getProductId());
            pstmt.setString(2, productSize.getSizeLabel());
            pstmt.setInt(3, productSize.getStockQuantity());
            pstmt.setBoolean(4, productSize.isAvailable());
            pstmt.setString(5, productSize.getSkuCode());
            pstmt.setInt(6, productSize.getProductSizeId());

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
    public boolean deleteProductSize(int productSizeId) {
        boolean isDeleted = false;
        String query = "DELETE FROM product_sizes WHERE product_size_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productSizeId);
            
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
    public ProductSize getProductSizeById(int productSizeId) {
        ProductSize productSize = null;
        String query = "SELECT * FROM product_sizes WHERE product_size_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productSizeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    productSize = extractProductSizeFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productSize;
    }

    @Override
    public List<ProductSize> getSizesByProductId(int productId) {
        List<ProductSize> sizes = new ArrayList<>();
        String query = "SELECT * FROM product_sizes WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sizes.add(extractProductSizeFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizes;
    }

    @Override
    public boolean updateStockQuantity(int productSizeId, int newQuantity) {
        boolean isUpdated = false;
        // Also smartly updates 'is_available' to false if stock drops to 0
        String query = "UPDATE product_sizes SET stock_quantity = ?, is_available = ? WHERE product_size_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, newQuantity);
            pstmt.setBoolean(2, newQuantity > 0); 
            pstmt.setInt(3, productSizeId);

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
    public boolean isStockAvailable(int productSizeId, int requestedQuantity) {
        boolean available = false;
        String query = "SELECT stock_quantity FROM product_sizes WHERE product_size_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productSizeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int currentStock = rs.getInt("stock_quantity");
                    if (currentStock >= requestedQuantity) {
                        available = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return available;
    }

    // Helper method to keep code DRY
    private ProductSize extractProductSizeFromResultSet(ResultSet rs) throws SQLException {
        ProductSize productSize = new ProductSize();
        productSize.setProductSizeId(rs.getInt("product_size_id"));
        productSize.setProductId(rs.getInt("product_id"));
        productSize.setSizeLabel(rs.getString("size_label"));
        productSize.setStockQuantity(rs.getInt("stock_quantity"));
        productSize.setAvailable(rs.getBoolean("is_available"));
        productSize.setSkuCode(rs.getString("sku_code"));
        return productSize;
    }
}