package com.fashionstore.model;

import java.sql.Timestamp;

public class CartItem {
    
    private int cartItemId;
    private int cartId;
    private int productId;
    private String sizeLabel;
    private int quantity;
    private double unitPrice;
    private Timestamp addedAt;

    // 1. Default Constructor
    public CartItem() {
    }

    // 2. Full Constructor (For retrieving from database)
    public CartItem(int cartItemId, int cartId, int productId, String sizeLabel, 
                    int quantity, double unitPrice, Timestamp addedAt) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.sizeLabel = sizeLabel;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.addedAt = addedAt;
    }

    // 3. Constructor without cartItemId and addedAt (For inserting a new item into the cart)
    public CartItem(int cartId, int productId, String sizeLabel, int quantity, double unitPrice) {
        this.cartId = cartId;
        this.productId = productId;
        this.sizeLabel = sizeLabel;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // 4. Getters and Setters
    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getSizeLabel() { return sizeLabel; }
    public void setSizeLabel(String sizeLabel) { this.sizeLabel = sizeLabel; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public Timestamp getAddedAt() { return addedAt; }
    public void setAddedAt(Timestamp addedAt) { this.addedAt = addedAt; }

    // 5. toString() method
    @Override
    public String toString() {
        return "CartItem [cartItemId=" + cartItemId + ", cartId=" + cartId + 
               ", productId=" + productId + ", size=" + sizeLabel + ", qty=" + quantity + "]";
    }
}