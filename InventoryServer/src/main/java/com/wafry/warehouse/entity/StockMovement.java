package com.wafry.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    
    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType type;
    
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

