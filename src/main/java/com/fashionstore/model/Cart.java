package com.fashionstore.model;

import java.sql.Timestamp;

public class Cart {
    
    private int cartId;
    private int userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 1. Default Constructor
    public Cart() {
    }

    // 2. Full Constructor (For retrieving from database)
    public Cart(int cartId, int userId, Timestamp createdAt, Timestamp updatedAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 3. Constructor for creating a new cart (DB handles the timestamps and ID)
    public Cart(int userId) {
        this.userId = userId;
    }

    // 4. Getters and Setters
    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    // 5. toString() method
    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", userId=" + userId + ", updated=" + updatedAt + "]";
    }
}