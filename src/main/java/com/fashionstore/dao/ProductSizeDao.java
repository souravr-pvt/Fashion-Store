package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.ProductSize;

public interface ProductSizeDao {
    
    // Core CRUD Operations
    boolean addProductSize(ProductSize productSize);
    boolean updateProductSize(ProductSize productSize);
    boolean deleteProductSize(int productSizeId);
    
    // Fetching Data
    ProductSize getProductSizeById(int productSizeId);
    List<ProductSize> getSizesByProductId(int productId); // Crucial for displaying size options on the product page
    
    // Inventory Management
    boolean updateStockQuantity(int productSizeId, int newQuantity);
    boolean isStockAvailable(int productSizeId, int requestedQuantity);
}