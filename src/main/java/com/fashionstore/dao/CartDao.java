package com.fashionstore.dao;

import com.fashionstore.model.Cart;

public interface CartDao {
    
    // Core Operations
    // Returns the generated cart ID
    int createCart(Cart cart); 
    
    // Fetching Data
    Cart getCartById(int cartId);
    Cart getCartByUserId(int userId); // Crucial for finding a logged-in user's active cart
    
    // Removing Data (e.g., deleting the cart after a successful checkout)
    boolean deleteCart(int cartId);
}