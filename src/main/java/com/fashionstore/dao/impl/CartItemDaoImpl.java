package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartItemDao;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDaoImpl implements CartItemDao {

    @Override
    public boolean addCartItem(CartItem cartItem) {
        boolean isAdded = false;
        String query = "INSERT INTO cart_items (cart_id, product_id, size_label, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartItem.getCartId());
            pstmt.setInt(2, cartItem.getProductId());
            pstmt.setString(3, cartItem.getSizeLabel());
            pstmt.setInt(4, cartItem.getQuantity());
            pstmt.setDouble(5, cartItem.getUnitPrice());

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
    public boolean updateCartItemQuantity(int cartItemId, int newQuantity) {
        boolean isUpdated = false;
        String query = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, cartItemId);

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
    public boolean removeCartItem(int cartItemId) {
        boolean isRemoved = false;
        String query = "DELETE FROM cart_items WHERE cart_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartItemId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isRemoved = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRemoved;
    }

    @Override
    public boolean removeAllCartItemsByCartId(int cartId) {
        boolean isRemoved = false;
        String query = "DELETE FROM cart_items WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isRemoved = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRemoved;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart_items WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(extractCartItemFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        CartItem cartItem = null;
        String query = "SELECT * FROM cart_items WHERE cart_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartItemId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cartItem = extractCartItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItem;
    }

    @Override
    public CartItem getCartItemByCartAndProductSize(int cartId, int productId, String sizeLabel) {
        CartItem cartItem = null;
        String query = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ? AND size_label = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cartId);
            pstmt.setInt(2, productId);
            pstmt.setString(3, sizeLabel);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cartItem = extractCartItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItem;
    }

    // Helper method to keep code DRY
    private CartItem extractCartItemFromResultSet(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setCartId(rs.getInt("cart_id"));
        cartItem.setProductId(rs.getInt("product_id"));
        cartItem.setSizeLabel(rs.getString("size_label"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setUnitPrice(rs.getDouble("unit_price"));
        cartItem.setAddedAt(rs.getTimestamp("added_at"));
        return cartItem;
    }
}