package com.orbytex.orbytex1.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false)
    private Integer quantityOnHand = 0;

    @Column(nullable = false)
    private Integer quantityReserved = 0;

    @Column(nullable = false)
    private Integer quantityAvailable = 0;

    @Column(length = 100)
    private String warehouseLocation;

    @Column
    private LocalDateTime lastCountedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        this.quantityAvailable = this.quantityOnHand - this.quantityReserved;
    }

    public void addStock(Integer quantity) {
        this.quantityOnHand += quantity;
        this.quantityAvailable = this.quantityOnHand - this.quantityReserved;
    }

    public void removeStock(Integer quantity) {
        if (this.quantityAvailable >= quantity) {
            this.quantityOnHand -= quantity;
            this.quantityAvailable = this.quantityOnHand - this.quantityReserved;
        } else {
            throw new IllegalArgumentException("Insufficient stock available");
        }
    }
}

