package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderItemDao;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDaoImpl implements OrderItemDao {

    @Override
    public boolean addOrderItem(OrderItem orderItem) {
        boolean isAdded = false;
        String query = "INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, subtotal, size_label) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getProductId());
            pstmt.setString(3, orderItem.getProductName());
            pstmt.setInt(4, orderItem.getQuantity());
            pstmt.setDouble(5, orderItem.getUnitPrice());
            pstmt.setDouble(6, orderItem.getSubtotal());
            pstmt.setString(7, orderItem.getSizeLabel());

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
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, orderId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orderItems.add(extractOrderItemFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        OrderItem orderItem = null;
        String query = "SELECT * FROM order_items WHERE order_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, orderItemId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    orderItem = extractOrderItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItem;
    }

    // Helper method to keep code DRY
    private OrderItem extractOrderItemFromResultSet(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setProductName(rs.getString("product_name"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setUnitPrice(rs.getDouble("unit_price"));
        orderItem.setSubtotal(rs.getDouble("subtotal"));
        orderItem.setSizeLabel(rs.getString("size_label"));
        return orderItem;
    }
}