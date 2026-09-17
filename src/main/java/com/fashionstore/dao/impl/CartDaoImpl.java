package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fashionstore.dao.CartDao;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;

public class CartDaoImpl implements CartDao {

    @Override
    public int createCart(Cart cart) {
        int generatedCartId = -1;
        // We only insert user_id; created_at and updated_at are handled by MySQL timestamps
        String query = "INSERT INTO cart (user_id) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, cart.getUserId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedCartId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedCartId;
    }

    @Override
    public Cart getCartById(int cartId) {
        Cart cart = null;
        String query = "SELECT * FROM cart WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cart = extractCartFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public Cart getCartByUserId(int userId) {
        Cart cart = null;
        String query = "SELECT * FROM cart WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cart = extractCartFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public boolean deleteCart(int cartId) {
        boolean isDeleted = false;
        String query = "DELETE FROM cart WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    // Helper method to keep code DRY
    private Cart extractCartFromResultSet(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setCreatedAt(rs.getTimestamp("created_at"));
        cart.setUpdatedAt(rs.getTimestamp("updated_at"));
        return cart;
    }
}