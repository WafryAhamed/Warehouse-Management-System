package com.orbytex.orbytex1.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
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
}

