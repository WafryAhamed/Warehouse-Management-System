package com.wafry.warehouse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock", uniqueConstraints = {
    @UniqueConstraint(name = "uk_stock", columnNames = {"product_id", "warehouse_id", "location_id"})
})
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    
    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;
    
    @Column(name = "location_id")
    private Integer locationId;
    
    @Column(nullable = false)
    private Integer quantity = 0;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public Stock() {}

    public Stock(Integer productId, Integer warehouseId, Integer quantity) {
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Integer warehouseId) { this.warehouseId = warehouseId; }

    public Integer getLocationId() { return locationId; }
    public void setLocationId(Integer locationId) { this.locationId = locationId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}

