package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.CartItem;

public interface CartItemDao {
    
    // Core Operations
    boolean addCartItem(CartItem cartItem);
    boolean updateCartItemQuantity(int cartItemId, int newQuantity);
    
    // Removing Data
    boolean removeCartItem(int cartItemId); // Remove a single item
    boolean removeAllCartItemsByCartId(int cartId); // Clear the whole cart (useful after checkout)
    
    // Fetching Data
    List<CartItem> getCartItemsByCartId(int cartId); // To display the cart page
    CartItem getCartItemById(int cartItemId);
    
    // Helper method to check if an item already exists in the cart before adding
    CartItem getCartItemByCartAndProductSize(int cartId, int productId, String sizeLabel); 
}