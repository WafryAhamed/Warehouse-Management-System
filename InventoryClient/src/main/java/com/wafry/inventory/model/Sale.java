package com.wafry.inventory.model;

import java.time.LocalDateTime;

/**
 * Sale - Entity class representing a sales transaction
 *
 * @author Wafry Team
 */
public class Sale {
    private Integer id;
    private String saleCode;
    private Integer userId;
    private Integer branchId;
    private Double subtotal;
    private Double taxAmount;
    private Double discountAmount;
    private Double totalAmount;
    private String paymentMethod; // CASH, CARD, CHEQUE
    private String status; // COMPLETED, PENDING, CANCELLED
    private String notes;
    private LocalDateTime saleDate;
    private LocalDateTime createdAt;

    // Constructors
    public Sale() {}

    public Sale(String saleCode, Integer userId, Double totalAmount) {
        this.saleCode = saleCode;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = "COMPLETED";
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSaleCode() { return saleCode; }
    public void setSaleCode(String saleCode) { this.saleCode = saleCode; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getBranchId() { return branchId; }
    public void setBranchId(Integer branchId) { this.branchId = branchId; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(Double taxAmount) { this.taxAmount = taxAmount; }

    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", saleCode='" + saleCode + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

