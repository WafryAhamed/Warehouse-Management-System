package com.wafry.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "po_number", nullable = false, unique = true, length = 100)
    private String poNumber;
    
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;
    
    @Column(nullable = false)
    private String status = "PENDING";
    
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    
    @Column(name = "expected_delivery")
    private LocalDateTime expectedDelivery;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

