package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderDao;
import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

public class OrderDaoImpl implements OrderDao {

    @Override
    public int createOrder(Order order) {
        int generatedOrderId = -1; // Default to -1 to indicate failure
        
        // We omit order_date because MySQL will auto-generate it with DEFAULT CURRENT_TIMESTAMP
        String query = "INSERT INTO orders (user_id, total_amount, payment_method, order_status, delivery_address) VALUES (?, ?, ?, ?, ?)";

        // Notice the Statement.RETURN_GENERATED_KEYS flag here
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setString(3, order.getPaymentMethod());
            pstmt.setString(4, order.getOrderStatus());
            pstmt.setString(5, order.getDeliveryAddress());

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Retrieve the auto-generated order_id
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedOrderId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedOrderId;
    }

    @Override
    public Order getOrderById(int orderId) {
        Order order = null;
        String query = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, orderId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    order = extractOrderFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        // Order by order_date DESC so the newest orders show up first
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(extractOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders ORDER BY order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                orders.add(extractOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String newStatus) {
        boolean isUpdated = false;
        String query = "UPDATE orders SET order_status = ? WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    // Helper method to keep code DRY
    private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setDeliveryAddress(rs.getString("delivery_address"));
        return order;
    }
}