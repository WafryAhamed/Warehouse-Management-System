package com.wafry.inventory.config;

/**
 * ApplicationConfig - Central configuration for the application
 *
 * Manages:
 * - Application properties (title, version, etc.)
 * - Window dimensions
 * - API configuration
 * - Logging settings
 *
 * @author Wafry Team
 */
public class ApplicationConfig {

    // Application Properties
    public static final String APP_TITLE = "Inventory Management System - Enterprise Edition";
    public static final String APP_VERSION = "2.0-STABLE";
    public static final String APP_NAME = "inventory-desktop-app";

    // Window Properties
    public static final int DEFAULT_WINDOW_WIDTH = 1200;
    public static final int DEFAULT_WINDOW_HEIGHT = 700;
    public static final int MIN_WINDOW_WIDTH = 1200;
    public static final int MIN_WINDOW_HEIGHT = 700;

    // API Configuration
    public static final String API_BASE_URL = System.getenv("INVENTORY_API_URL") != null
            ? System.getenv("INVENTORY_API_URL")
            : "http://localhost:8080/api/v1";
    public static final int API_TIMEOUT_MS = 5000;
    public static final String API_CONTENT_TYPE = "application/json";

    // Logging Configuration
    public static final boolean ENABLE_DEBUG_LOGGING = Boolean.parseBoolean(
            System.getenv("DEBUG_MODE") != null ? System.getenv("DEBUG_MODE") : "false"
    );
    public static final String LOG_LEVEL = "INFO";

    // UI Configuration
    public static final String CSS_RESOURCE_PATH = "/com/wafry/inventory/styles.css";
    public static final String FXML_RESOURCE_PATH = "/com/wafry/inventory/fxml/";

    // Feature Flags
    public static final boolean ENABLE_PRODUCT_IMPORT = true;
    public static final boolean ENABLE_PRODUCT_EXPORT = true;
    public static final boolean ENABLE_MULTI_BRANCH = true;

    /**
     * Initialize application configuration
     * Can load from external properties files if needed
     */
    public static void initialize() {
        // Validate configuration
        if (API_BASE_URL == null || API_BASE_URL.isEmpty()) {
            throw new IllegalStateException("API Base URL must be configured");
        }
    }

    /**
     * Get API endpoint URL
     * @param endpoint The endpoint path (e.g., "/products")
     * @return Full API URL
     */
    public static String getApiEndpoint(String endpoint) {
        return API_BASE_URL + endpoint;
    }
}

