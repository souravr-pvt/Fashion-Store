package com.fashionstore.model;

public class Product {
    private int id;
    private int categoryId;
    private String productName;
    private String description;
    private double discountPercentage;
    private String imageUrl;
    private boolean isActive;
    private double price;
    private int stock;
    private String brand; // <--- MAKE SURE THIS EXISTS
    private String color; // <--- AND THIS

    // Constructors
    public Product() {}

    public Product(int id, int categoryId, String productName, String description, double discountPercentage,
                   String imageUrl, boolean isActive, double price, int stock, String brand, String color) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.color = color;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    // THESE TWO ARE CRITICAL FOR THE JSP TO STOP CRASHING:
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}