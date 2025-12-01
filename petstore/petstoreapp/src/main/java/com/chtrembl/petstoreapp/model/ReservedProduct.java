package com.chtrembl.petstoreapp.model;


import lombok.Data;

@Data
public class ReservedProduct {

    private String productId;
    private String name;
    private int quantity;

    // Getters and setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}