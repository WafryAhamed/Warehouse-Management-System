package com.wafry.inventory.util;

import java.time.LocalDateTime;

/**
 * TokenManager - Manages authentication tokens
 *
 * Responsibilities:
 * - Store and retrieve JWT tokens
 * - Check token validity
 * - Handle token expiration
 * - Manage token refresh
 *
 * @author Wafry Team
 */
public class TokenManager {
    private static final Logger logger = Logger.getLogger(TokenManager.class);

    private static String currentToken;
    private static LocalDateTime tokenExpiry;
    private static String refreshToken;
    private static final long TOKEN_EXPIRY_MINUTES = 60; // Default token expiry

    /**
     * Store authentication token
     *
     * @param token The JWT token
     */
    public static void setToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        currentToken = token;
        tokenExpiry = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);
        logger.info("Token stored, expiry set to: " + tokenExpiry);
    }

    /**
     * Get current token
     *
     * @return The current token or null
     */
    public static String getToken() {
        if (isTokenExpired()) {
            clearToken();
            return null;
        }
        return currentToken;
    }

    /**
     * Check if token is currently valid
     *
     * @return true if token exists and is not expired
     */
    public static boolean hasValidToken() {
        return currentToken != null && !isTokenExpired();
    }

    /**
     * Check if token is expired
     *
     * @return true if token is expired
     */
    public static boolean isTokenExpired() {
        if (tokenExpiry == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(tokenExpiry);
    }

    /**
     * Get token expiry time
     *
     * @return Expiry LocalDateTime
     */
    public static LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    /**
     * Set refresh token for token renewal
     *
     * @param token The refresh token
     */
    public static void setRefreshToken(String token) {
        refreshToken = token;
        logger.debug("Refresh token stored");
    }

    /**
     * Get refresh token
     *
     * @return The refresh token or null
     */
    public static String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Clear all tokens (logout)
     */
    public static void clearToken() {
        currentToken = null;
        tokenExpiry = null;
        refreshToken = null;
        logger.info("Tokens cleared - user logged out");
    }

    /**
     * Get Authorization header value
     *
     * @return "Bearer <token>" or null if no valid token
     */
    public static String getAuthorizationHeader() {
        String token = getToken();
        if (token != null) {
            return "Bearer " + token;
        }
        return null;
    }

    /**
     * Get time until expiry in minutes
     *
     * @return Minutes until expiry, 0 if expired
     */
    public static long getMinutesUntilExpiry() {
        if (tokenExpiry == null || isTokenExpired()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.MINUTES.between(LocalDateTime.now(), tokenExpiry);
    }
}

