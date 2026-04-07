package com.wafry.inventory.model;

import java.time.LocalDateTime;

/**
 * Product - Entity class representing a product in the inventory
 *
 * Properties:
 * - Basic info (name, SKU, barcode)
 * - Pricing (price, cost)
 * - Stock management (quantity, min/max levels)
 * - Categorization and descriptions
 *
 * @author Wafry Team
 */
public class Product {
    private Integer id;
    private String name;
    private String sku;
    private String barcode;
    private Double price;
    private Double cost;
    private Integer categoryId;
    private String category;
    private String description;
    private String imageUrl;
    private Integer stock;
    private Integer minStock;
    private Integer maxStock;
    private String unit;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public Product() {}

    public Product(Integer id, String name, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.sku = "SKU-" + id;
        this.price = price;
        this.stock = stock;
        this.status = "ACTIVE";
    }

    public Product(String name, String sku, Double price, Integer stock) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.stock = stock;
        this.status = "ACTIVE";
    }

    public Product(String name, String sku, Double price, Integer categoryId, Integer stock) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.categoryId = categoryId;
        this.stock = stock;
        this.status = "ACTIVE";
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }

    public Integer getMaxStock() { return maxStock; }
    public void setMaxStock(Integer maxStock) { this.maxStock = maxStock; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return name + " (SKU: " + sku + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
