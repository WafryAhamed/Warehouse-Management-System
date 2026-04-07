package com.wafry.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

// ============ USER ENTITY ============
@Entity
@Table(name = "users", indexes = {@Index(name = "idx_username", columnList = "username", unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password; // BCrypt encrypted
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role; // ADMIN, STAFF
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private Boolean active = true;
}

enum UserRole {
    ADMIN, STAFF
}

// ============ PRODUCT ENTITY ============
@Entity
@Table(name = "products", indexes = {@Index(name = "idx_sku", columnList = "sku", unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true, length = 100)
    private String sku;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private BigDecimal cost;
    
    @Column(name = "supplier_id")
    private Integer supplierId;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

// ============ WAREHOUSE ENTITY ============
@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(name = "current_stock")
    private Integer currentStock = 0;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

// ============ LOCATION (BIN/RACK/SHELF) ENTITY ============
@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;
    
    @Column(nullable = false, length = 50)
    private String rackNumber;
    
    @Column(nullable = false, length = 50)
    private String shelfNumber;
    
    @Column(nullable = false, length = 50)
    private String binNumber;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(name = "current_stock")
    private Integer currentStock = 0;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

// ============ STOCK ENTITY ============
@Entity
@Table(name = "stock", uniqueConstraints = {
    @UniqueConstraint(name = "uk_stock", columnNames = {"product_id", "warehouse_id", "location_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Stock {
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
}

// ============ STOCK MOVEMENT ENTITY ============
@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    
    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType type; // IN, OUT, TRANSFER, ADJUSTMENT
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "from_location_id")
    private Integer fromLocationId;
    
    @Column(name = "to_location_id")
    private Integer toLocationId;
    
    @Column(name = "reference_number", length = 100)
    private String referenceNumber;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "user_id")
    private Integer userId;
}

enum MovementType {
    IN, OUT, TRANSFER, ADJUSTMENT
}

// ============ SUPPLIER ENTITY ============
@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String address;
    
    @Column(name = "lead_time_days")
    private Integer leadTimeDays = 7;
    
    @Column(nullable = false)
    private String status = "ACTIVE";
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

// ============ PURCHASE ORDER ENTITY ============
@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "po_number", nullable = false, unique = true, length = 100)
    private String poNumber;
    
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;
    
    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, CONFIRMED, RECEIVED, CANCELLED
    
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    
    @Column(name = "expected_delivery")
    private LocalDateTime expectedDelivery;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

// ============ SALES ORDER ENTITY ============
@Entity
@Table(name = "sales_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "so_number", nullable = false, unique = true, length = 100)
    private String soNumber;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    
    @Column(name = "expected_delivery")
    private LocalDateTime expectedDelivery;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

// ============ ORDER ITEM ENTITY ============
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    
    @Column(name = "order_type", nullable = false)
    private String orderType; // PURCHASE, SALES
}

