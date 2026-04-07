package com.wafry.inventory.service;

import com.wafry.inventory.exception.AuthenticationException;
import com.wafry.inventory.model.User;
import com.wafry.inventory.util.Logger;
import com.wafry.inventory.util.TokenManager;

/**
 * AuthService - Authentication service for user login and session management
 *
 * Responsibilities:
 * - Validate user credentials
 * - Manage authentication tokens (JWT)
 * - Track login sessions
 * - Handle token refresh
 * - Manage logout
 *
 * Note: Currently uses mock authentication for development.
 *       For production, integrate with JWT-based authentication from backend.
 *
 * @author Wafry Team
 */
public class AuthService {
    private static final Logger logger = Logger.getLogger(AuthService.class);

    private User currentUser;

    /**
     * Authenticate user with credentials
     *
     * @param username User username
     * @param password User password
     * @return Authentication successful
     * @throws AuthenticationException if authentication fails
     */
    public boolean authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationException("Username cannot be empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationException("Password cannot be empty");
        }

        // Mock authentication - replace with actual backend call
        if ("admin".equals(username) && "password".equals(password)) {
            currentUser = new User(username, "admin@inventory.com", "Administrator", "ADMIN");
            currentUser.setId(1);

            // Generate and store token
            String token = generateToken(currentUser);
            TokenManager.setToken(token);

            logger.info("User authenticated: " + username);
            return true;
        }

        // Test credentials
        if ("user".equals(username) && "user".equals(password)) {
            currentUser = new User(username, "user@inventory.com", "Standard User", "STAFF");
            currentUser.setId(2);

            // Generate and store token
            String token = generateToken(currentUser);
            TokenManager.setToken(token);

            logger.info("User authenticated: " + username);
            return true;
        }

        logger.warn("Authentication failed for user: " + username);
        throw new AuthenticationException("Invalid username or password");
    }

    /**
     * Check if user is currently authenticated
     *
     * @return true if user has valid token
     */
    public boolean isAuthenticated() {
        return TokenManager.hasValidToken();
    }

    /**
     * Get currently logged-in user
     *
     * @return User object or null if not authenticated
     */
    public User getCurrentUser() {
        if (!isAuthenticated()) {
            return null;
        }
        return currentUser;
    }

    /**
     * Get current username
     *
     * @return Username or null if not authenticated
     */
    public String getCurrentUsername() {
        if (!isAuthenticated()) {
            return null;
        }
        return currentUser != null ? currentUser.getUsername() : null;
    }

    /**
     * Get authentication token
     *
     * @return Auth token or null if not authenticated
     */
    public String getAuthToken() {
        return TokenManager.getToken();
    }

    /**
     * Get authorization header
     *
     * @return "Bearer <token>" or null if not authenticated
     */
    public String getAuthorizationHeader() {
        return TokenManager.getAuthorizationHeader();
    }

    /**
     * Check if token is expired
     *
     * @return true if token is expired
     */
    public boolean isTokenExpired() {
        return TokenManager.isTokenExpired();
    }

    /**
     * Get time until token expiry
     *
     * @return Minutes until expiry
     */
    public long getMinutesUntilExpiry() {
        return TokenManager.getMinutesUntilExpiry();
    }

    /**
     * Refresh authentication token
     *
     * @return New token or null if refresh failed
     */
    public String refreshToken() {
        if (currentUser == null) {
            logger.warn("Cannot refresh token - no current user");
            return null;
        }

        String newToken = generateToken(currentUser);
        TokenManager.setToken(newToken);
        logger.info("Token refreshed for user: " + currentUser.getUsername());
        return newToken;
    }

    /**
     * Logout current user
     */
    public void logout() {
        if (isAuthenticated()) {
            logger.info("User logged out: " + (currentUser != null ? currentUser.getUsername() : "unknown"));
        }
        currentUser = null;
        TokenManager.clearToken();
    }

    /**
     * Generate authentication token
     * In production, this should return a JWT token from the backend
     *
     * @param user The user to generate token for
     * @return Generated token
     */
    private String generateToken(User user) {
        // Mock token generation - in production, use JWT
        String token = "jwt_" + user.getId() + "_" + System.currentTimeMillis() + "_" +
                      user.getUsername().hashCode();
        logger.debug("Token generated for user: " + user.getUsername());
        return token;
    }
}

