package com.wafry.inventory.api;

import com.wafry.inventory.config.ApplicationConfig;
import com.wafry.inventory.exception.ApiException;
import com.wafry.inventory.model.Product;
import com.wafry.inventory.util.JsonUtil;
import com.wafry.inventory.util.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * InventoryApiClient - REST API client for backend communication
 *
 * Handles all HTTP communication with the inventory management backend API.
 * Provides methods for CRUD operations on products and other resources.
 *
 * Configuration:
 * - Base URL: Configured in ApplicationConfig
 * - Timeout: 5 seconds
 * - Content-Type: application/json
 *
 * @author Wafry Team
 */
public class InventoryApiClient {
    private static final Logger logger = Logger.getLogger(InventoryApiClient.class);

    /**
     * Fetch all products from the backend
     * GET /api/products
     *
     * @return List of all products
     * @throws ApiException If API call fails
     */
    public List<Product> getAllProducts() throws ApiException {
        try {
            String url = ApplicationConfig.getApiEndpoint("/products");
            logger.info("Fetching all products from: " + url);

            HttpURLConnection conn = createConnection(url, "GET");
            int responseCode = conn.getResponseCode();
            String responseBody = readResponseBody(conn);

            if (responseCode == 200) {
                logger.info("Successfully fetched products");
                return JsonUtil.parseProductArray(responseBody);
            } else {
                throw new ApiException(
                        "Failed to fetch products. Status: " + responseCode,
                        responseCode,
                        responseBody
                );
            }
        } catch (IOException e) {
            logger.error("Network error while fetching products", e);
            throw new ApiException("Network error while fetching products: " + e.getMessage(), e);
        }
    }

    /**
     * Fetch a single product by ID
     * GET /api/products/{id}
     *
     * @param id The product ID
     * @return The product
     * @throws ApiException If API call fails
     */
    public Product getProductById(Integer id) throws ApiException {
        try {
            String url = ApplicationConfig.getApiEndpoint("/products/" + id);
            logger.info("Fetching product: " + id);

            HttpURLConnection conn = createConnection(url, "GET");
            int responseCode = conn.getResponseCode();
            String responseBody = readResponseBody(conn);

            if (responseCode == 200) {
                return JsonUtil.parseProductObject(responseBody);
            } else {
                throw new ApiException(
                        "Failed to fetch product. Status: " + responseCode,
                        responseCode,
                        responseBody
                );
            }
        } catch (IOException e) {
            logger.error("Network error while fetching product", e);
            throw new ApiException("Network error while fetching product: " + e.getMessage(), e);
        }
    }

    /**
     * Save a new product
     * POST /api/products
     *
     * @param product The product to save
     * @return The saved product with ID
     * @throws ApiException If API call fails
     */
    public Product saveProduct(Product product) throws ApiException {
        try {
            String url = ApplicationConfig.getApiEndpoint("/products");
            logger.info("Saving new product: " + product.getName());

            HttpURLConnection conn = createConnection(url, "POST");
            conn.setDoOutput(true);

            String jsonBody = JsonUtil.productToJson(product);
            writeRequestBody(conn, jsonBody);

            int responseCode = conn.getResponseCode();
            String responseBody = readResponseBody(conn);

            if (responseCode == 200 || responseCode == 201) {
                logger.info("Product saved successfully");
                return JsonUtil.parseProductObject(responseBody);
            } else {
                throw new ApiException(
                        "Failed to save product. Status: " + responseCode,
                        responseCode,
                        responseBody
                );
            }
        } catch (IOException e) {
            logger.error("Network error while saving product", e);
            throw new ApiException("Network error while saving product: " + e.getMessage(), e);
        }
    }

    /**
     * Update an existing product
     * PUT /api/products/{id}
     *
     * @param id The product ID
     * @param product The updated product data
     * @return The updated product
     * @throws ApiException If API call fails
     */
    public Product updateProduct(Integer id, Product product) throws ApiException {
        try {
            String url = ApplicationConfig.getApiEndpoint("/products/" + id);
            logger.info("Updating product: " + id);

            HttpURLConnection conn = createConnection(url, "PUT");
            conn.setDoOutput(true);

            String jsonBody = JsonUtil.productToJson(product);
            writeRequestBody(conn, jsonBody);

            int responseCode = conn.getResponseCode();
            String responseBody = readResponseBody(conn);

            if (responseCode == 200) {
                logger.info("Product updated successfully");
                return JsonUtil.parseProductObject(responseBody);
            } else {
                throw new ApiException(
                        "Failed to update product. Status: " + responseCode,
                        responseCode,
                        responseBody
                );
            }
        } catch (IOException e) {
            logger.error("Network error while updating product", e);
            throw new ApiException("Network error while updating product: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a product
     * DELETE /api/products/{id}
     *
     * @param id The product ID
     * @throws ApiException If API call fails
     */
    public void deleteProduct(Integer id) throws ApiException {
        try {
            String url = ApplicationConfig.getApiEndpoint("/products/" + id);
            logger.info("Deleting product: " + id);

            HttpURLConnection conn = createConnection(url, "DELETE");
            int responseCode = conn.getResponseCode();
            String responseBody = readResponseBody(conn);

            if (responseCode != 200 && responseCode != 204) {
                throw new ApiException(
                        "Failed to delete product. Status: " + responseCode,
                        responseCode,
                        responseBody
                );
            }
            logger.info("Product deleted successfully");

        } catch (IOException e) {
            logger.error("Network error while deleting product", e);
            throw new ApiException("Network error while deleting product: " + e.getMessage(), e);
        }
    }

    /**
     * Create and configure an HTTP connection
     */
    private HttpURLConnection createConnection(String urlString, String method) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", ApplicationConfig.API_CONTENT_TYPE);
        conn.setConnectTimeout(ApplicationConfig.API_TIMEOUT_MS);
        conn.setReadTimeout(ApplicationConfig.API_TIMEOUT_MS);

        return conn;
    }

    /**
     * Write JSON request body to connection
     */
    private void writeRequestBody(HttpURLConnection conn, String jsonBody) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    /**
     * Read response body from HTTP connection
     */
    private String readResponseBody(HttpURLConnection conn) throws IOException {
        InputStream is;
        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            is = conn.getErrorStream();
            if (is == null) {
                return "";
            }
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }
}

