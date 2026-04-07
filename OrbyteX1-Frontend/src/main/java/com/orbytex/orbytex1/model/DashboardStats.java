package com.orbytex.orbytex1.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {
    private BigDecimal totalRevenue;
    private Integer totalOrders;
    private Integer totalCustomers;
    private Integer totalProducts;
    private Integer lowStockProducts;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;
    private Integer activeUsers;
    private LocalDateTime lastUpdated;
}

