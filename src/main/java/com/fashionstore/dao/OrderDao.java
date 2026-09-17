package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Order;

public interface OrderDao {
    
    // Core Operations
    // Returns the generated order ID so we can link order items to it
    int createOrder(Order order); 
    
    // Fetching Data
    Order getOrderById(int orderId);
    List<Order> getOrdersByUserId(int userId); // For the user's "My Orders" history page
    List<Order> getAllOrders(); // For the admin panel to view all customer orders
    
    // Updating Data
    boolean updateOrderStatus(int orderId, String newStatus); // e.g., "Pending" -> "Shipped" -> "Delivered"
}