package com.wafry.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
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

