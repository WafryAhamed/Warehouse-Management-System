package com.wafry.inventory.util;

import com.wafry.inventory.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonUtil - Lightweight JSON parsing and serialization utility
 *
 * Provides basic JSON handling without external dependencies.
 * Note: For production, consider using a full JSON library like Jackson or Gson.
 *
 * @author Wafry Team
 */
public class JsonUtil {
    private static final Logger logger = Logger.getLogger(JsonUtil.class);

    /**
     * Parse a JSON array string into a list of Product objects
     *
     * @param jsonString The JSON array string
     * @return List of Product objects
     */
    public static List<Product> parseProductArray(String jsonString) {
        List<Product> products = new ArrayList<>();

        if (jsonString == null || jsonString.isEmpty()) {
            return products;
        }

        try {
            Pattern objectPattern = Pattern.compile("\\{[^{}]*\\}");
            Matcher objectMatcher = objectPattern.matcher(jsonString);

            while (objectMatcher.find()) {
                String jsonObject = objectMatcher.group();
                Product product = parseProductObject(jsonObject);
                if (product != null) {
                    products.add(product);
                }
            }
        } catch (Exception e) {
            logger.error("Error parsing product array", e);
        }

        return products;
    }

    /**
     * Parse a single JSON object string into a Product
     *
     * @param jsonString The JSON object string
     * @return Product object or null if parsing fails
     */
    public static Product parseProductObject(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        try {
            Product product = new Product();

            Integer id = extractIntValue(jsonString, "id");
            if (id != null) product.setId(id);

            String name = extractStringValue(jsonString, "name");
            if (name != null) product.setName(name);

            Double price = extractDoubleValue(jsonString, "price");
            if (price != null) product.setPrice(price);

            String sku = extractStringValue(jsonString, "sku");
            if (sku != null) product.setSku(sku);

            Integer stock = extractIntValue(jsonString, "stock");
            if (stock != null) product.setStock(stock);

            String category = extractStringValue(jsonString, "category");
            if (category != null) product.setCategory(category);

            Double cost = extractDoubleValue(jsonString, "cost");
            if (cost != null) product.setCost(cost);

            return product;
        } catch (Exception e) {
            logger.error("Error parsing product JSON: " + jsonString, e);
            return null;
        }
    }

    /**
     * Extract integer value from JSON
     */
    private static Integer extractIntValue(String json, String key) {
        try {
            if (json == null || key == null) return null;
            Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?\\d+)");
            Matcher matcher = pattern.matcher(json);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            logger.debug("Error extracting int value for key: " + key);
        }
        return null;
    }

    /**
     * Extract double value from JSON
     */
    private static Double extractDoubleValue(String json, String key) {
        try {
            if (json == null || key == null) return null;
            Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?[\\d.eE+-]+)");
            Matcher matcher = pattern.matcher(json);
            if (matcher.find()) {
                String value = matcher.group(1);
                if (value != null && !value.isEmpty()) {
                    return Double.parseDouble(value);
                }
            }
        } catch (Exception e) {
            logger.debug("Error extracting double value for key: " + key);
        }
        return null;
    }

    /**
     * Extract string value from JSON
     */
    private static String extractStringValue(String json, String key) {
        try {
            if (json == null || key == null) return null;
            Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
            Matcher matcher = pattern.matcher(json);
            if (matcher.find()) {
                String value = matcher.group(1);
                if (value != null && !value.isEmpty()) {
                    // Unescape common JSON escape sequences
                    return value.replace("\\\"", "\"")
                                .replace("\\\\", "\\")
                                .replace("\\n", "\n")
                                .replace("\\r", "\r")
                                .replace("\\t", "\t");
                }
            }
        } catch (Exception e) {
            logger.debug("Error extracting string value for key: " + key);
        }
        return null;
    }

    /**
     * Convert Product to JSON string
     *
     * @param product The product to convert
     * @return JSON representation of the product
     */
    public static String productToJson(Product product) {
        StringBuilder json = new StringBuilder();
        json.append("{");

        if (product.getId() != null) {
            json.append("\"id\": ").append(product.getId()).append(", ");
        }

        json.append("\"name\": \"").append(escapeJson(product.getName())).append("\", ");
        json.append("\"sku\": \"").append(escapeJson(product.getSku())).append("\", ");
        json.append("\"price\": ").append(product.getPrice()).append(", ");
        json.append("\"stock\": ").append(product.getStock());

        if (product.getCategory() != null) {
            json.append(", \"category\": \"").append(escapeJson(product.getCategory())).append("\"");
        }

        if (product.getCost() != null) {
            json.append(", \"cost\": ").append(product.getCost());
        }

        json.append("}");
        return json.toString();
    }

    /**
     * Escape special characters in JSON strings
     */
    private static String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

