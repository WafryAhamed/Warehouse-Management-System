package com.wafry.inventory.service;

import com.wafry.inventory.api.InventoryApiClient;
import com.wafry.inventory.exception.ApiException;
import com.wafry.inventory.model.Supplier;
import com.wafry.inventory.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * SupplierService - Business logic for supplier operations
 *
 * Responsibilities:
 * - Manage supplier information
 * - Link suppliers to products
 * - Manage supplier relationships
 * - Track supplier performance
 *
 * @author Wafry Team
 */
public class SupplierService {
    private static final Logger logger = Logger.getLogger(SupplierService.class);
    private final InventoryApiClient apiClient;

    public SupplierService() {
        this.apiClient = new InventoryApiClient();
    }

    /**
     * Get all suppliers
     *
     * @return List of all suppliers
     * @throws ApiException if API call fails
     */
    public List<Supplier> getAllSuppliers() throws ApiException {
        logger.info("Retrieving all suppliers");
        try {
            // TODO: Implement API call to /api/suppliers
            return new ArrayList<>(); // Placeholder
        } catch (Exception e) {
            logger.error("Error retrieving suppliers", e);
            throw new ApiException("Failed to retrieve suppliers: " + e.getMessage(), e);
        }
    }

    /**
     * Get supplier by ID
     *
     * @param id Supplier ID
     * @return The supplier
     * @throws ApiException if API call fails
     */
    public Supplier getSupplierById(Integer id) throws ApiException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid supplier ID");
        }
        logger.info("Retrieving supplier: " + id);
        try {
            // TODO: Implement API call to /api/suppliers/{id}
            return null; // Placeholder
        } catch (Exception e) {
            logger.error("Error retrieving supplier", e);
            throw new ApiException("Failed to retrieve supplier: " + e.getMessage(), e);
        }
    }

    /**
     * Create new supplier
     *
     * @param supplier The supplier to create
     * @return The created supplier with ID
     * @throws ApiException if API call fails
     */
    public Supplier createSupplier(Supplier supplier) throws ApiException {
        validateSupplier(supplier);
        logger.info("Creating new supplier: " + supplier.getSupplierName());

        try {
            // TODO: Implement API call to POST /api/suppliers
            return supplier; // Placeholder
        } catch (Exception e) {
            logger.error("Error creating supplier", e);
            throw new ApiException("Failed to create supplier: " + e.getMessage(), e);
        }
    }

    /**
     * Update supplier
     *
     * @param id Supplier ID
     * @param supplier Updated supplier data
     * @return The updated supplier
     * @throws ApiException if API call fails
     */
    public Supplier updateSupplier(Integer id, Supplier supplier) throws ApiException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid supplier ID");
        }
        validateSupplier(supplier);
        logger.info("Updating supplier: " + id);

        try {
            // TODO: Implement API call to PUT /api/suppliers/{id}
            return supplier; // Placeholder
        } catch (Exception e) {
            logger.error("Error updating supplier", e);
            throw new ApiException("Failed to update supplier: " + e.getMessage(), e);
        }
    }

    /**
     * Delete supplier
     *
     * @param id Supplier ID
     * @throws ApiException if API call fails
     */
    public void deleteSupplier(Integer id) throws ApiException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid supplier ID");
        }
        logger.info("Deleting supplier: " + id);

        try {
            // TODO: Implement API call to DELETE /api/suppliers/{id}
        } catch (Exception e) {
            logger.error("Error deleting supplier", e);
            throw new ApiException("Failed to delete supplier: " + e.getMessage(), e);
        }
    }

    /**
     * Get active suppliers
     *
     * @return List of active suppliers
     * @throws ApiException if API call fails
     */
    public List<Supplier> getActiveSuppliers() throws ApiException {
        logger.info("Retrieving active suppliers");
        try {
            return getAllSuppliers().stream()
                    .filter(s -> Boolean.TRUE.equals(s.getIsActive()))
                    .toList();
        } catch (Exception e) {
            logger.error("Error retrieving active suppliers", e);
            throw new ApiException("Failed to retrieve active suppliers: " + e.getMessage(), e);
        }
    }

    /**
     * Search suppliers by name or email
     *
     * @param query Search query
     * @return List of matching suppliers
     * @throws ApiException if API call fails
     */
    public List<Supplier> searchSuppliers(String query) throws ApiException {
        if (query == null || query.trim().isEmpty()) {
            return getAllSuppliers();
        }

        logger.info("Searching suppliers: " + query);
        String searchTerm = query.toLowerCase().trim();

        try {
            return getAllSuppliers().stream()
                    .filter(s -> s.getSupplierName().toLowerCase().contains(searchTerm) ||
                               (s.getEmail() != null && s.getEmail().toLowerCase().contains(searchTerm)) ||
                               (s.getContactPerson() != null && s.getContactPerson().toLowerCase().contains(searchTerm)))
                    .toList();
        } catch (Exception e) {
            logger.error("Error searching suppliers", e);
            throw new ApiException("Failed to search suppliers: " + e.getMessage(), e);
        }
    }

    /**
     * Validate supplier data
     *
     * @param supplier The supplier to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null");
        }
        if (supplier.getSupplierName() == null || supplier.getSupplierName().isEmpty()) {
            throw new IllegalArgumentException("Supplier name is required");
        }
        if (supplier.getEmail() == null || supplier.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (supplier.getPhone() == null || supplier.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (!isValidEmail(supplier.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    /**
     * Simple email validation
     *
     * @param email The email to validate
     * @return true if email format is valid
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

