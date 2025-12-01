package com.chtrembl.petstoreapp.model;

import lombok.Data;

import java.util.List;
@Data
public class ReservedOrder {
    private String sessionId;
    private String orderId;
    private List<ReservedProduct> products;

    // Getters and setters
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public List<ReservedProduct> getProducts() { return products; }
    public void setProducts(List<ReservedProduct> products) { this.products = products; }
}