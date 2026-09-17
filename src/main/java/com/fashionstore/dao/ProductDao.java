package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Product;

public interface ProductDao {
    
    // Core CRUD Operations
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
    Product getProductById(int productId);
    
    // Fetching Lists of Products
    List<Product> getAllProducts();
    List<Product> getActiveProducts(); // For displaying on the frontend
    List<Product> getProductsByCategoryId(int categoryId); // For filtering
    List<Product> searchProducts(String keyword);
    List<Product> filterProducts(String keyword, String sort, Double minPrice, Double maxPrice, String[] brands, String[] colors);

}