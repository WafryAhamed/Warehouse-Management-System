package com.wafry.warehouse.dto;

public class DashboardStatsDTO {
    private int totalProducts;
    private int totalWarehouses;
    private int totalStock;
    private int lowStockCount;
    private double totalValue;
    private double utilizationPercent;
    private long totalMovements;

    public DashboardStatsDTO() {}

    public int getTotalProducts() { return totalProducts; }
    public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }

    public int getTotalWarehouses() { return totalWarehouses; }
    public void setTotalWarehouses(int totalWarehouses) { this.totalWarehouses = totalWarehouses; }

    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) { this.totalStock = totalStock; }

    public int getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(int lowStockCount) { this.lowStockCount = lowStockCount; }

    public double getTotalValue() { return totalValue; }
    public void setTotalValue(double totalValue) { this.totalValue = totalValue; }

    public double getUtilizationPercent() { return utilizationPercent; }
    public void setUtilizationPercent(double utilizationPercent) { this.utilizationPercent = utilizationPercent; }

    public long getTotalMovements() { return totalMovements; }
    public void setTotalMovements(long totalMovements) { this.totalMovements = totalMovements; }
}

