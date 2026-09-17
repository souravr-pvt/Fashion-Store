package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Category;

public interface CategoryDao {
    
    // Core CRUD Operations
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(int categoryId);
    
    // Fetching Categories
    Category getCategoryById(int categoryId);
    List<Category> getAllCategories(); // For the admin panel to see everything
    List<Category> getActiveCategories(); // For the frontend user to see only active categories
}