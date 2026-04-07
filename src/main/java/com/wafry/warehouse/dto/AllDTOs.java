package com.wafry.warehouse.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// ============ AUTH DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDTO {
    private String username;
    private String password;
    private String email;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private String username;
    private String email;
    private String role;
    private Long expiresIn;
}

// ============ PRODUCT DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Integer id;
    private String name;
    private String sku;
    private String category;
    private BigDecimal price;
    private BigDecimal cost;
    private Integer supplierId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
    private String name;
    private String sku;
    private String category;
    private BigDecimal price;
    private BigDecimal cost;
    private Integer supplierId;
}

// ============ WAREHOUSE DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {
    private Integer id;
    private String name;
    private String location;
    private Integer capacity;
    private Integer currentStock;
    private Double utilizationPercent;
    private LocalDateTime createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseCreateRequest {
    private String name;
    private String location;
    private Integer capacity;
}

// ============ LOCATION DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {
    private Integer id;
    private Integer warehouseId;
    private String rackNumber;
    private String shelfNumber;
    private String binNumber;
    private Integer capacity;
    private Integer currentStock;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationCreateRequest {
    private Integer warehouseId;
    private String rackNumber;
    private String shelfNumber;
    private String binNumber;
    private Integer capacity;
}

// ============ STOCK DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDTO {
    private Integer productId;
    private String productName;
    private Integer warehouseId;
    private String warehouseName;
    private Integer quantity;
    private Integer locationId;
    private LocalDateTime lastUpdated;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustRequest {
    private Integer productId;
    private Integer warehouseId;
    private Integer quantity;
    private String reference;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferRequest {
    private Integer productId;
    private Integer fromWarehouse;
    private Integer toWarehouse;
    private Integer quantity;
    private String reference;
}

// ============ MOVEMENT DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer warehouseId;
    private String type;
    private Integer quantity;
    private String reference;
    private LocalDateTime createdAt;
}

// ============ SUPPLIER DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer leadTimeDays;
    private String status;
    private LocalDateTime createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCreateRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer leadTimeDays;
}

// ============ ORDER DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDTO {
    private Integer id;
    private String poNumber;
    private Integer supplierId;
    private String supplierName;
    private String status;
    private BigDecimal total;
    private LocalDateTime expectedDelivery;
    private LocalDateTime createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderCreateRequest {
    private String poNumber;
    private Integer supplierId;
    private BigDecimal total;
    private LocalDateTime expectedDelivery;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrderDTO {
    private Integer id;
    private String soNumber;
    private String customerName;
    private String status;
    private BigDecimal total;
    private LocalDateTime expectedDelivery;
    private LocalDateTime createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderCreateRequest {
    private String soNumber;
    private String customerName;
    private BigDecimal total;
    private LocalDateTime expectedDelivery;
}

// ============ USER DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;
}

// ============ DASHBOARD DTOs ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDTO {
    private Integer totalProducts;
    private Integer totalWarehouses;
    private Integer totalStock;
    private Integer lowStockCount;
    private Double totalInventoryValue;
    private Double utilizationPercent;
}

// ============ ERROR DTO ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String path;
}

// ============ GENERIC RESPONSE ============
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO<T> {
    private Boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
}

