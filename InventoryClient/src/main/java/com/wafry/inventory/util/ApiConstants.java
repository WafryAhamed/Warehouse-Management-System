package com.wafry.inventory.util;

/**
 * ApiConstants - Centralized API endpoint constants
 *
 * Contains all API endpoints and endpoint patterns
 * for easier maintenance and consistency.
 *
 * @author Wafry Team
 */
public class ApiConstants {
    // Base paths
    public static final String PRODUCTS_ENDPOINT = "/products";
    public static final String SALES_ENDPOINT = "/sales";
    public static final String SUPPLIERS_ENDPOINT = "/suppliers";
    public static final String USERS_ENDPOINT = "/users";
    public static final String AUTH_ENDPOINT = "/auth";
    public static final String STOCK_MOVEMENTS_ENDPOINT = "/stock-movements";

    // Auth endpoints
    public static final String LOGIN = AUTH_ENDPOINT + "/login";
    public static final String LOGOUT = AUTH_ENDPOINT + "/logout";
    public static final String REFRESH_TOKEN = AUTH_ENDPOINT + "/refresh";
    public static final String VALIDATE_TOKEN = AUTH_ENDPOINT + "/validate";

    // Product endpoints
    public static final String GET_ALL_PRODUCTS = PRODUCTS_ENDPOINT;
    public static final String GET_PRODUCT = PRODUCTS_ENDPOINT + "/{id}";
    public static final String CREATE_PRODUCT = PRODUCTS_ENDPOINT;
    public static final String UPDATE_PRODUCT = PRODUCTS_ENDPOINT + "/{id}";
    public static final String DELETE_PRODUCT = PRODUCTS_ENDPOINT + "/{id}";
    public static final String GET_LOW_STOCK = PRODUCTS_ENDPOINT + "/low-stock";
    public static final String SEARCH_PRODUCTS = PRODUCTS_ENDPOINT + "/search";

    // Sales endpoints
    public static final String GET_ALL_SALES = SALES_ENDPOINT;
    public static final String GET_SALE = SALES_ENDPOINT + "/{id}";
    public static final String CREATE_SALE = SALES_ENDPOINT;
    public static final String GET_SALES_SUMMARY = SALES_ENDPOINT + "/summary";

    // Supplier endpoints
    public static final String GET_ALL_SUPPLIERS = SUPPLIERS_ENDPOINT;
    public static final String GET_SUPPLIER = SUPPLIERS_ENDPOINT + "/{id}";
    public static final String CREATE_SUPPLIER = SUPPLIERS_ENDPOINT;
    public static final String UPDATE_SUPPLIER = SUPPLIERS_ENDPOINT + "/{id}";
    public static final String DELETE_SUPPLIER = SUPPLIERS_ENDPOINT + "/{id}";

    // Stock movement endpoints
    public static final String GET_STOCK_HISTORY = STOCK_MOVEMENTS_ENDPOINT + "/{productId}";
    public static final String CREATE_STOCK_MOVEMENT = STOCK_MOVEMENTS_ENDPOINT;

    // Query parameters
    public static final String PARAM_PAGE = "?page=";
    public static final String PARAM_LIMIT = "&limit=";
    public static final String PARAM_SEARCH = "?search=";
    public static final String PARAM_STATUS = "&status=";

    /**
     * Build full endpoint URL
     *
     * @param endpoint The endpoint path
     * @return Full URL
     */
    public static String buildUrl(String endpoint) {
        return endpoint;
    }

    /**
     * Build endpoint with ID
     *
     * @param endpoint The endpoint pattern
     * @param id The resource ID
     * @return Endpoint with ID
     */
    public static String buildUrlWithId(String endpoint, Integer id) {
        return endpoint.replace("{id}", String.valueOf(id));
    }
}

