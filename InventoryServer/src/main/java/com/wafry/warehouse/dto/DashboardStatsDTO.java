package com.wafry.warehouse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DashboardStatsDTO {
    private BigDecimal totalRevenue;
    private Integer totalOrders;
    private Integer totalCustomers;
    private Integer totalProducts;
    private Integer lowStockProducts;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;
    private Integer activeUsers;
    private LocalDateTime lastUpdated;

    public DashboardStatsDTO() {}

    public DashboardStatsDTO(BigDecimal totalRevenue, Integer totalOrders, Integer totalCustomers,
                            Integer totalProducts, Integer lowStockProducts, BigDecimal totalExpenses,
                            BigDecimal netProfit, Integer activeUsers, LocalDateTime lastUpdated) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalCustomers = totalCustomers;
        this.totalProducts = totalProducts;
        this.lowStockProducts = lowStockProducts;
        this.totalExpenses = totalExpenses;
        this.netProfit = netProfit;
        this.activeUsers = activeUsers;
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Integer getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Integer totalOrders) { this.totalOrders = totalOrders; }

    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }

    public Integer getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Integer totalProducts) { this.totalProducts = totalProducts; }

    public Integer getLowStockProducts() { return lowStockProducts; }
    public void setLowStockProducts(Integer lowStockProducts) { this.lowStockProducts = lowStockProducts; }

    public BigDecimal getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(BigDecimal totalExpenses) { this.totalExpenses = totalExpenses; }

    public BigDecimal getNetProfit() { return netProfit; }
    public void setNetProfit(BigDecimal netProfit) { this.netProfit = netProfit; }

    public Integer getActiveUsers() { return activeUsers; }
    public void setActiveUsers(Integer activeUsers) { this.activeUsers = activeUsers; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public static DashboardStatsDTOBuilder builder() {
        return new DashboardStatsDTOBuilder();
    }

    public static class DashboardStatsDTOBuilder {
        private BigDecimal totalRevenue;
        private Integer totalOrders;
        private Integer totalCustomers;
        private Integer totalProducts;
        private Integer lowStockProducts;
        private BigDecimal totalExpenses;
        private BigDecimal netProfit;
        private Integer activeUsers;
        private LocalDateTime lastUpdated;

        public DashboardStatsDTOBuilder totalRevenue(BigDecimal totalRevenue) {
            this.totalRevenue = totalRevenue;
            return this;
        }

        public DashboardStatsDTOBuilder totalOrders(Integer totalOrders) {
            this.totalOrders = totalOrders;
            return this;
        }

        public DashboardStatsDTOBuilder totalCustomers(Integer totalCustomers) {
            this.totalCustomers = totalCustomers;
            return this;
        }

        public DashboardStatsDTOBuilder totalProducts(Integer totalProducts) {
            this.totalProducts = totalProducts;
            return this;
        }

        public DashboardStatsDTOBuilder lowStockProducts(Integer lowStockProducts) {
            this.lowStockProducts = lowStockProducts;
            return this;
        }

        public DashboardStatsDTOBuilder totalExpenses(BigDecimal totalExpenses) {
            this.totalExpenses = totalExpenses;
            return this;
        }

        public DashboardStatsDTOBuilder netProfit(BigDecimal netProfit) {
            this.netProfit = netProfit;
            return this;
        }

        public DashboardStatsDTOBuilder activeUsers(Integer activeUsers) {
            this.activeUsers = activeUsers;
            return this;
        }

        public DashboardStatsDTOBuilder lastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public DashboardStatsDTO build() {
            return new DashboardStatsDTO(totalRevenue, totalOrders, totalCustomers, totalProducts,
                    lowStockProducts, totalExpenses, netProfit, activeUsers, lastUpdated);
        }
    }
}

