package com.fashionstore.model;

public class Category {
    
    private int categoryId;
    private String categoryName;
    private String description;
    private boolean isActive;

    // 1. Default Constructor
    public Category() {
    }

    // 2. Full Constructor
    public Category(int categoryId, String categoryName, String description, boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.isActive = isActive;
    }

    // 3. Constructor without categoryId (for creating new categories)
    public Category(String categoryName, String description, boolean isActive) {
        this.categoryName = categoryName;
        this.description = description;
        this.isActive = isActive;
    }

    // 4. Getters and Setters
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // 5. toString() method
    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", isActive=" + isActive + "]";
    }
}