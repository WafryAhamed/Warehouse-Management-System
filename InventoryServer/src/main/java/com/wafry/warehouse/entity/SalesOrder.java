package com.wafry.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "sales_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "so_number", nullable = false, unique = true, length = 100)
    private String soNumber;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
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

