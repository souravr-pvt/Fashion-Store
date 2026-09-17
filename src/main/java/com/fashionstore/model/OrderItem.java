package com.fashionstore.model;

public class OrderItem {
    
    private int orderItemId;
    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    private String sizeLabel;

    // 1. Default Constructor
    public OrderItem() {
    }

    // 2. Full Constructor (For retrieving from database)
    public OrderItem(int orderItemId, int orderId, int productId, String productName, 
                     int quantity, double unitPrice, double subtotal, String sizeLabel) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.sizeLabel = sizeLabel;
    }

    // 3. Constructor without orderItemId (For inserting a new order item)
    public OrderItem(int orderId, int productId, String productName, int quantity, 
                     double unitPrice, double subtotal, String sizeLabel) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.sizeLabel = sizeLabel;
    }

    // 4. Getters and Setters
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public String getSizeLabel() { return sizeLabel; }
    public void setSizeLabel(String sizeLabel) { this.sizeLabel = sizeLabel; }

    // 5. toString() method
    @Override
    public String toString() {
        return "OrderItem [orderItemId=" + orderItemId + ", orderId=" + orderId + 
               ", product=" + productName + ", qty=" + quantity + ", subtotal=" + subtotal + "]";
    }
}