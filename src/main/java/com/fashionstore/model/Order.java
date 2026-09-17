package com.fashionstore.model;

import java.sql.Timestamp;
import java.util.List; // ADDED THIS IMPORT
// Make sure this matches your package structure if CartItem is somewhere else:
import com.fashionstore.model.CartItem; // ADDED THIS IMPORT

public class Order {
    
    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private double totalAmount;
    private String paymentMethod;
    private String orderStatus;
    private String deliveryAddress;
    
    // ADDED THIS VARIABLE:
    private List<CartItem> items; 

    // 1. Default Constructor
    public Order() {
    }

    // 2. Full Constructor (For retrieving from database)
    public Order(int orderId, int userId, Timestamp orderDate, double totalAmount, 
                 String paymentMethod, String orderStatus, String deliveryAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
    }

    // 3. Constructor without orderId and orderDate
    public Order(int userId, double totalAmount, String paymentMethod, 
                 String orderStatus, String deliveryAddress) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
    }

    // 4. Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    // ADDED THESE GETTERS AND SETTERS FOR ITEMS:
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    // 5. toString() method
    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", userId=" + userId + ", totalAmount=" + totalAmount + 
               ", status=" + orderStatus + ", date=" + orderDate + "]";
    }
}