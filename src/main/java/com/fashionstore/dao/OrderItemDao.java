package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.OrderItem;

public interface OrderItemDao {
    
    // Core Operations
    boolean addOrderItem(OrderItem orderItem);
    
    // Fetching Data
    List<OrderItem> getOrderItemsByOrderId(int orderId); // Crucial for displaying the items on an order receipt/details page
    OrderItem getOrderItemById(int orderItemId);
}