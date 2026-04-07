package com.wafry.inventory.service;

/**
 * InventorySummary - Summary data for inventory dashboard
 *
 * Provides aggregated statistics about products in inventory.
 *
 * @author Wafry Team
 */
public class InventorySummary {
    private int totalProducts;
    private double totalValue;
    private int totalQuantity;
    private int lowStockCount;
    private int activeProducts;

    // Constructors
    public InventorySummary() {}

    public InventorySummary(int totalProducts, double totalValue, int totalQuantity, int lowStockCount, int activeProducts) {
        this.totalProducts = totalProducts;
        this.totalValue = totalValue;
        this.totalQuantity = totalQuantity;
        this.lowStockCount = lowStockCount;
        this.activeProducts = activeProducts;
    }

    // Getters & Setters
    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getLowStockCount() {
        return lowStockCount;
    }

    public void setLowStockCount(int lowStockCount) {
        this.lowStockCount = lowStockCount;
    }

    public int getActiveProducts() {
        return activeProducts;
    }

    public void setActiveProducts(int activeProducts) {
        this.activeProducts = activeProducts;
    }

    @Override
    public String toString() {
        return "InventorySummary{" +
                "totalProducts=" + totalProducts +
                ", totalValue=" + totalValue +
                ", totalQuantity=" + totalQuantity +
                ", lowStockCount=" + lowStockCount +
                ", activeProducts=" + activeProducts +
                '}';
    }
}

