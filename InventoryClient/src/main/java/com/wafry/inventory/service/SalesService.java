package com.wafry.inventory.service;

import com.wafry.inventory.api.InventoryApiClient;
import com.wafry.inventory.exception.ApiException;
import com.wafry.inventory.model.Sale;
import com.wafry.inventory.util.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SalesService - Business logic for sales operations
 *
 * Responsibilities:
 * - Manage sales transactions
 * - Calculate totals
 * - Generate invoices
 * - Track sales history
 * - Provide sales analytics
 *
 * @author Wafry Team
 */
public class SalesService {
    private static final Logger logger = Logger.getLogger(SalesService.class);
    private final InventoryApiClient apiClient;

    public SalesService() {
        this.apiClient = new InventoryApiClient();
    }

    /**
     * Get all sales records
     *
     * @return List of sales
     * @throws ApiException if API call fails
     */
    public List<Sale> getAllSales() throws ApiException {
        logger.info("Retrieving all sales");
        try {
            // TODO: Implement API call to /api/sales
            return new ArrayList<>(); // Placeholder
        } catch (Exception e) {
            logger.error("Error retrieving sales", e);
            throw new ApiException("Failed to retrieve sales: " + e.getMessage(), e);
        }
    }

    /**
     * Get sale by ID
     *
     * @param id Sale ID
     * @return The sale
     * @throws ApiException if API call fails
     */
    public Sale getSaleById(Integer id) throws ApiException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid sale ID");
        }
        logger.info("Retrieving sale: " + id);
        try {
            // TODO: Implement API call to /api/sales/{id}
            return null; // Placeholder
        } catch (Exception e) {
            logger.error("Error retrieving sale", e);
            throw new ApiException("Failed to retrieve sale: " + e.getMessage(), e);
        }
    }

    /**
     * Create new sale
     *
     * @param sale The sale to create
     * @return The created sale with ID
     * @throws ApiException if API call fails
     */
    public Sale createSale(Sale sale) throws ApiException {
        validateSale(sale);
        logger.info("Creating new sale: " + sale.getSaleCode());

        try {
            // Calculate totals
            calculateSaleTotals(sale);

            // TODO: Implement API call to POST /api/sales
            return sale; // Placeholder
        } catch (Exception e) {
            logger.error("Error creating sale", e);
            throw new ApiException("Failed to create sale: " + e.getMessage(), e);
        }
    }

    /**
     * Calculate sale totals (subtotal, tax, discount, total)
     *
     * @param sale The sale to calculate
     */
    public void calculateSaleTotals(Sale sale) {
        if (sale.getSubtotal() == null) {
            sale.setSubtotal(0.0);
        }

        Double taxAmount = sale.getTaxAmount() != null ? sale.getTaxAmount() : 0.0;
        Double discountAmount = sale.getDiscountAmount() != null ? sale.getDiscountAmount() : 0.0;

        Double total = sale.getSubtotal() + taxAmount - discountAmount;
        sale.setTotalAmount(total);

        logger.debug("Sale totals calculated - Subtotal: " + sale.getSubtotal() +
                ", Tax: " + taxAmount + ", Discount: " + discountAmount + ", Total: " + total);
    }

    /**
     * Get sales summary (total sales, count, etc.)
     *
     * @return Sales summary data
     * @throws ApiException if API call fails
     */
    public SalesSummary getSalesSummary() throws ApiException {
        logger.info("Retrieving sales summary");

        try {
            List<Sale> allSales = getAllSales();

            SalesSummary summary = new SalesSummary();
            summary.setTotalSales(allSales.size());
            summary.setTotalRevenue(allSales.stream()
                    .mapToDouble(s -> s.getTotalAmount() != null ? s.getTotalAmount() : 0)
                    .sum());
            summary.setAverageSaleAmount(summary.getTotalRevenue() / Math.max(summary.getTotalSales(), 1));

            return summary;
        } catch (Exception e) {
            logger.error("Error retrieving sales summary", e);
            throw new ApiException("Failed to retrieve sales summary: " + e.getMessage(), e);
        }
    }

    /**
     * Get recent sales
     *
     * @param limit Number of recent sales to retrieve
     * @return List of recent sales
     * @throws ApiException if API call fails
     */
    public List<Sale> getRecentSales(int limit) throws ApiException {
        logger.info("Retrieving recent " + limit + " sales");

        try {
            List<Sale> allSales = getAllSales();
            return allSales.stream()
                    .sorted((s1, s2) -> {
                        LocalDateTime time1 = s1.getSaleDate() != null ? s1.getSaleDate() : s1.getCreatedAt();
                        LocalDateTime time2 = s2.getSaleDate() != null ? s2.getSaleDate() : s2.getCreatedAt();
                        return time2.compareTo(time1);
                    })
                    .limit(limit)
                    .toList();
        } catch (Exception e) {
            logger.error("Error retrieving recent sales", e);
            throw new ApiException("Failed to retrieve recent sales: " + e.getMessage(), e);
        }
    }

    /**
     * Validate sale data
     *
     * @param sale The sale to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale cannot be null");
        }
        if (sale.getSaleCode() == null || sale.getSaleCode().isEmpty()) {
            throw new IllegalArgumentException("Sale code is required");
        }
        if (sale.getUserId() == null || sale.getUserId() <= 0) {
            throw new IllegalArgumentException("Valid user ID is required");
        }
        if (sale.getTotalAmount() == null || sale.getTotalAmount() < 0) {
            throw new IllegalArgumentException("Total amount must be a positive number");
        }
    }

    /**
     * Inner class for sales summary
     */
    public static class SalesSummary {
        private int totalSales;
        private double totalRevenue;
        private double averageSaleAmount;

        public int getTotalSales() {
            return totalSales;
        }

        public void setTotalSales(int totalSales) {
            this.totalSales = totalSales;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(double totalRevenue) {
            this.totalRevenue = totalRevenue;
        }

        public double getAverageSaleAmount() {
            return averageSaleAmount;
        }

        public void setAverageSaleAmount(double averageSaleAmount) {
            this.averageSaleAmount = averageSaleAmount;
        }
    }
}

