package com.wafry.inventory.util;

/**
 * ValidationUtil - Input validation utility
 *
 * Provides common validation methods for forms and data.
 *
 * @author Wafry Team
 */
public class ValidationUtil {
    private static final Logger logger = Logger.getLogger(ValidationUtil.class);

    /**
     * Validate required field (not null or empty)
     *
     * @param value The value to validate
     * @param fieldName The field name for error message
     * @throws IllegalArgumentException if validation fails
     */
    public static void validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    /**
     * Validate email format
     *
     * @param email The email to validate
     * @return true if email format is valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Validate email and throw exception if invalid
     *
     * @param email The email to validate
     * @throws IllegalArgumentException if email is invalid
     */
    public static void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }

    /**
     * Validate numeric value
     *
     * @param value The value to check
     * @return true if value is numeric
     */
    public static boolean isNumeric(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate numeric value and throw exception if invalid
     *
     * @param value The value to validate
     * @param fieldName The field name for error message
     * @throws IllegalArgumentException if value is not numeric
     */
    public static void validateNumeric(String value, String fieldName) {
        if (!isNumeric(value)) {
            throw new IllegalArgumentException(fieldName + " must be a numeric value");
        }
    }

    /**
     * Validate positive number
     *
     * @param value The value to check
     * @return true if value is positive
     */
    public static boolean isPositive(Double value) {
        return value != null && value > 0;
    }

    /**
     * Validate positive number and throw exception if invalid
     *
     * @param value The value to validate
     * @param fieldName The field name for error message
     * @throws IllegalArgumentException if value is not positive
     */
    public static void validatePositive(Double value, String fieldName) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive number");
        }
    }

    /**
     * Validate positive integer
     *
     * @param value The value to check
     * @return true if value is positive
     */
    public static boolean isPositiveInteger(Integer value) {
        return value != null && value > 0;
    }

    /**
     * Validate minimum length
     *
     * @param value The value to validate
     * @param minLength Minimum length required
     * @return true if length is valid
     */
    public static boolean hasMinLength(String value, int minLength) {
        return value != null && value.length() >= minLength;
    }

    /**
     * Validate minimum length and throw exception if invalid
     *
     * @param value The value to validate
     * @param minLength Minimum length required
     * @param fieldName The field name for error message
     * @throws IllegalArgumentException if length is invalid
     */
    public static void validateMinLength(String value, int minLength, String fieldName) {
        if (!hasMinLength(value, minLength)) {
            throw new IllegalArgumentException(fieldName + " must be at least " + minLength + " characters");
        }
    }

    /**
     * Validate maximum length
     *
     * @param value The value to validate
     * @param maxLength Maximum length allowed
     * @return true if length is valid
     */
    public static boolean hasMaxLength(String value, int maxLength) {
        return value != null && value.length() <= maxLength;
    }

    /**
     * Validate maximum length and throw exception if invalid
     *
     * @param value The value to validate
     * @param maxLength Maximum length allowed
     * @param fieldName The field name for error message
     * @throws IllegalArgumentException if length is invalid
     */
    public static void validateMaxLength(String value, int maxLength, String fieldName) {
        if (!hasMaxLength(value, maxLength)) {
            throw new IllegalArgumentException(fieldName + " must not exceed " + maxLength + " characters");
        }
    }

    /**
     * Validate phone number format
     *
     * @param phone The phone number to validate
     * @return true if phone format looks valid
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        // Simple validation: at least 10 digits
        String digits = phone.replaceAll("[^0-9]", "");
        return digits.length() >= 10;
    }

    /**
     * Validate phone number and throw exception if invalid
     *
     * @param phone The phone number to validate
     * @throws IllegalArgumentException if phone is invalid
     */
    public static void validatePhone(String phone) {
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    /**
     * Validate URL format
     *
     * @param url The URL to validate
     * @return true if URL format is valid
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        return url.matches("^(https?://)?([a-zA-Z0-9.-]+)\\.[a-zA-Z]{2,}.*");
    }

    /**
     * Sanitize string input (remove special characters)
     *
     * @param input The input to sanitize
     * @return Sanitized string
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().replaceAll("[<>\"'%;&]", "");
    }
}

