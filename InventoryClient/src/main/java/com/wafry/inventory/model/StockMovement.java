package com.wafry.inventory.model;

import java.time.LocalDateTime;

/**
 * StockMovement - Entity class representing inventory stock movements
 *
 * @author Wafry Team
 */
public class StockMovement {
    private Integer id;
    private Integer productId;
    private Integer branchId;
    private Integer quantity;
    private String type; // IN, OUT, ADJUSTMENT
    private String reference; // SALE_ID, PURCHASE_ID, etc
    private String notes;
    private Integer userId;
    private LocalDateTime createdAt;

    // Constructors
    public StockMovement() {}

    public StockMovement(Integer productId, Integer quantity, String type, String reference) {
        this.productId = productId;
        this.quantity = quantity;
        this.type = type;
        this.reference = reference;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getBranchId() { return branchId; }
    public void setBranchId(Integer branchId) { this.branchId = branchId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                '}';
    }
}

