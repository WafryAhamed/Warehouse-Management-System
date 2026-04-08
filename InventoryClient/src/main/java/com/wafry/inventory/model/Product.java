package com.wafry.inventory.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal margin;
    private Integer reorderLevel;
    private String imageUrl;
    private Boolean isActive;
    private Integer quantityOnHand;
    private Integer quantityAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(Long id, String sku, String name, String description, Long categoryId, String categoryName,
                   Long brandId, String brandName, BigDecimal costPrice, BigDecimal sellingPrice,
                   BigDecimal margin, Integer reorderLevel, String imageUrl, Boolean isActive,
                   Integer quantityOnHand, Integer quantityAvailable, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.margin = margin;
        this.reorderLevel = reorderLevel;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.quantityOnHand = quantityOnHand;
        this.quantityAvailable = quantityAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public BigDecimal getCostPrice() { return costPrice; }
    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }

    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }

    public BigDecimal getMargin() { return margin; }
    public void setMargin(BigDecimal margin) { this.margin = margin; }

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Integer getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(Integer quantityOnHand) { this.quantityOnHand = quantityOnHand; }

    public Integer getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(Integer quantityAvailable) { this.quantityAvailable = quantityAvailable; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Convenience methods for compatibility
    public Double getPrice() {
        return sellingPrice != null ? sellingPrice.doubleValue() : null;
    }
    public void setPrice(Double price) {
        this.sellingPrice = price != null ? BigDecimal.valueOf(price) : null;
    }

    public Integer getStock() { return quantityOnHand; }
    public void setStock(Integer stock) { this.quantityOnHand = stock; }

    public String getCategory() { return categoryName; }
    public void setCategory(String category) { this.categoryName = category; }

    public Double getCost() {
        return costPrice != null ? costPrice.doubleValue() : null;
    }
    public void setCost(Double cost) {
        this.costPrice = cost != null ? BigDecimal.valueOf(cost) : null;
    }

    public String getStatus() {
        return isActive != null && isActive ? "ACTIVE" : "INACTIVE";
    }
    public void setStatus(String status) {
        this.isActive = "ACTIVE".equals(status);
    }
}
