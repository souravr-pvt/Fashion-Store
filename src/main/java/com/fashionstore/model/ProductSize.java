package com.fashionstore.model;

public class ProductSize {
    
    private int productSizeId;
    private int productId;
    private String sizeLabel;
    private int stockQuantity;
    private boolean isAvailable;
    private String skuCode;

    // 1. Default Constructor
    public ProductSize() {
    }

    // 2. Full Constructor
    public ProductSize(int productSizeId, int productId, String sizeLabel, int stockQuantity, boolean isAvailable, String skuCode) {
        this.productSizeId = productSizeId;
        this.productId = productId;
        this.sizeLabel = sizeLabel;
        this.stockQuantity = stockQuantity;
        this.isAvailable = isAvailable;
        this.skuCode = skuCode;
    }

    // 3. Constructor without productSizeId
    public ProductSize(int productId, String sizeLabel, int stockQuantity, boolean isAvailable, String skuCode) {
        this.productId = productId;
        this.sizeLabel = sizeLabel;
        this.stockQuantity = stockQuantity;
        this.isAvailable = isAvailable;
        this.skuCode = skuCode;
    }

    // 4. Getters and Setters
    public int getProductSizeId() { return productSizeId; }
    public void setProductSizeId(int productSizeId) { this.productSizeId = productSizeId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getSizeLabel() { return sizeLabel; }
    public void setSizeLabel(String sizeLabel) { this.sizeLabel = sizeLabel; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    // 5. toString() method
    @Override
    public String toString() {
        return "ProductSize [productSizeId=" + productSizeId + ", productId=" + productId + 
               ", size=" + sizeLabel + ", stock=" + stockQuantity + ", sku=" + skuCode + "]";
    }
}