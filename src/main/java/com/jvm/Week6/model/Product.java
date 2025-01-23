package com.jvm.Week6.model;

public class Product  {
    private String productId;
    private String title;
    private String description;

    public Product(String productId, String title, String description, String price, String discount) {
        this.productId = productId;
        this.title = title;
        this.description = description;
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}