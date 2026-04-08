package com.wafry.inventory.service;

import com.wafry.inventory.api.InventoryApiClient;
import com.wafry.inventory.exception.ApiException;
import com.wafry.inventory.model.Product;
import com.wafry.inventory.util.Logger;

import java.util.List;

/**
 * ProductService - Business logic for product operations
 *
 * Responsibilities:
 * - Manage product operations (CRUD)
 * - Apply business rules and validations
 * - Handle low stock alerts
 * - Track stock history
 * - Coordinate between UI controllers and API client
 *
 * @author Wafry Team
 */
public class ProductService {
    private static final Logger logger = Logger.getLogger(ProductService.class);
    private final InventoryApiClient apiClient;
    private static final int LOW_STOCK_THRESHOLD = 10; // Default threshold

    public ProductService() {
        this.apiClient = new InventoryApiClient();
    }

    /**
     * Get all products
     *
     * @return List of all products
     * @throws ApiException if API call fails
     */
    public List<Product> getAllProducts() throws ApiException {
        logger.info("Retrieving all products");
        return apiClient.getAllProducts();
    }

    /**
     * Get a product by ID
     *
     * @param id Product ID
     * @return The product
     * @throws ApiException if API call fails
     */
    public Product getProductById(Long id) throws ApiException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        logger.info("Retrieving product: " + id);
        return apiClient.getProductById(id);
    }

    /**
     * Create a new product
     *
     * @param product The product to create
     * @return The created product with ID
     * @throws ApiException if API call fails
     */
    public Product createProduct(Product product) throws ApiException {
        validateProduct(product);
        logger.info("Creating new product: " + product.getName());
        return apiClient.saveProduct(product);
    }

    /**
     * Update an existing product
     *
     * @param id Product ID
     * @param product The updated product data
     * @return The updated product
     * @throws ApiException if API call fails
     */
    public Product updateProduct(Long id, Product product) throws ApiException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        validateProduct(product);
        logger.info("Updating product: " + id);
        return apiClient.updateProduct(id, product);
    }

    /**
     * Delete a product
     *
     * @param id Product ID
     * @throws ApiException if API call fails
     */
    public void deleteProduct(Long id) throws ApiException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        logger.info("Deleting product: " + id);
        apiClient.deleteProduct(id);
    }

    /**
     * Search products by name or SKU
     *
     * @param query Search query
     * @return List of matching products
     * @throws ApiException if API call fails
     */
    public List<Product> searchProducts(String query) throws ApiException {
        if (query == null || query.trim().isEmpty()) {
            return getAllProducts();
        }
        logger.info("Searching products: " + query);
        List<Product> allProducts = getAllProducts();
        String searchTerm = query.toLowerCase().trim();
        return allProducts.stream()
                .filter(p -> (p.getName() != null && p.getName().toLowerCase().contains(searchTerm)) ||
                           (p.getSku() != null && p.getSku().toLowerCase().contains(searchTerm)))
                .toList();
    }

    /**
     * Get all products with low stock
     *
     * @return List of low stock products
     * @throws ApiException if API call fails
     */
    public List<Product> getLowStockProducts() throws ApiException {
        logger.info("Retrieving low stock products");
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(p -> p.getStock() != null && p.getStock() <= LOW_STOCK_THRESHOLD)
                .toList();
    }

    /**
     * Get products by category
     *
     * @param category The category name
     * @return List of products in category
     * @throws ApiException if API call fails
     */
    public List<Product> getProductsByCategory(String category) throws ApiException {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        logger.info("Retrieving products for category: " + category);
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                .toList();
    }

    /**
     * Get product inventory summary
     *
     * @return Inventory summary
     * @throws ApiException if API call fails
     */
    public InventorySummary getInventorySummary() throws ApiException {
        logger.info("Calculating inventory summary");
        List<Product> allProducts = getAllProducts();

        InventorySummary summary = new InventorySummary();
        summary.setTotalProducts(allProducts.size());
        summary.setTotalValue(allProducts.stream()
                .mapToDouble(p -> (p.getPrice() != null ? p.getPrice() : 0) * (p.getStock() != null ? p.getStock() : 0))
                .sum());
        summary.setTotalQuantity(allProducts.stream()
                .mapToInt(p -> p.getStock() != null ? p.getStock() : 0)
                .sum());
        summary.setLowStockCount((int) allProducts.stream()
                .filter(p -> p.getStock() != null && p.getStock() <= LOW_STOCK_THRESHOLD)
                .count());
        summary.setActiveProducts((int) allProducts.stream()
                .filter(p -> "ACTIVE".equalsIgnoreCase(p.getStatus()))
                .count());

        return summary;
    }

    /**
     * Check if product stock is low
     *
     * @param product The product to check
     * @return true if stock is below threshold
     */
    public boolean isLowStock(Product product) {
        return product != null && product.getStock() != null &&
               product.getStock() <= LOW_STOCK_THRESHOLD;
    }

    /**
     * Validate product data
     *
     * @param product The product to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            throw new IllegalArgumentException("SKU is required");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Price must be a positive number");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }
}
