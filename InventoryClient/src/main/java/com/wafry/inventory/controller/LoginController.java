package com.wafry.inventory.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import com.wafry.inventory.service.AuthService;
import com.wafry.inventory.util.Logger;
import com.wafry.inventory.util.SceneManager;
import com.wafry.inventory.exception.AuthenticationException;

/**
 * LoginController - Handles user authentication
 *
 * Responsibilities:
 * - Validate user credentials
 * - Authenticate users
 * - Navigate to main application after successful login
 * - Display authentication errors
 *
 * @author Wafry Team
 */
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private AuthService authService;

    /**
     * Initialize controller - called by JavaFX after FXML is loaded
     */
    @FXML
    public void initialize() {
        logger.info("Initializing LoginController");

        authService = new AuthService();
        errorLabel.setText("");

        setupKeyboardListeners();
    }

    /**
     * Setup keyboard event listeners for Enter key
     */
    private void setupKeyboardListeners() {
        usernameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onLoginButtonClick();
            }
        });

        passwordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onLoginButtonClick();
            }
        });
    }

    /**
     * Handle login button click
     */
    @FXML
    protected void onLoginButtonClick() {
        clearErrors();

        String username = (usernameField != null && usernameField.getText() != null)
                         ? usernameField.getText().trim() : "";
        String password = (passwordField != null && passwordField.getText() != null)
                         ? passwordField.getText().trim() : "";

        // Validate inputs
        if (!validateInputs(username, password)) {
            return;
        }

        // Attempt authentication
        try {
            boolean loginSuccess = authService.authenticate(username, password);

            if (loginSuccess) {
                logger.info("Login successful for user: " + username);
                navigateToInventory();
            }

        } catch (AuthenticationException e) {
            logger.warn("Authentication failed: " + e.getMessage());
            showError(e.getMessage());
            if (passwordField != null) {
                passwordField.clear();
            }
        } catch (Exception e) {
            logger.error("Unexpected error during login", e);
            showError("An unexpected error occurred. Please try again.");
        }
    }

    /**
     * Validate login form inputs
     *
     * @param username Username input
     * @param password Password input
     * @return true if inputs are valid
     */
    private boolean validateInputs(String username, String password) {
        if (username.isEmpty()) {
            showError("❌ Username is required");
            usernameField.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            showError("❌ Password is required");
            passwordField.requestFocus();
            return false;
        }

        if (username.length() < 3) {
            showError("❌ Username must be at least 3 characters");
            usernameField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Navigate to inventory view after successful login
     */
    private void navigateToInventory() {
        try {
            logger.info("=== NAVIGATION STARTED ===");
            logger.info("Attempting to load inventory-view.fxml");
            
            // Debug info
            logger.info("ClassLoader: " + this.getClass().getClassLoader());
            var testUrl = this.getClass().getResource("/com/wafry/inventory/fxml/inventory-view.fxml");
            logger.info("Resource test result: " + (testUrl != null ? "FOUND: " + testUrl : "NOT FOUND"));
            
            SceneManager.loadScene("inventory-view.fxml");
            logger.info("Successfully navigated to inventory view");
            logger.info("=== NAVIGATION COMPLETED ===");
        } catch (Exception e) {
            logger.error("=== NAVIGATION FAILED ===");
            logger.error("Exception type: " + e.getClass().getName());
            logger.error("Exception message: " + e.getMessage());
            logger.error("Full stack trace:", e);
            
            String errorMsg = e.getClass().getSimpleName() + ": " + e.getMessage();
            if (e.getCause() != null) {
                errorMsg += " -> Cause: " + e.getCause().getMessage();
            }
            showError(errorMsg);
        }
    }

    /**
     * Display error message
     */
    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setStyle("-fx-text-fill: #d32f2f;");
        }
    }

    /**
     * Clear error message
     */
    private void clearErrors() {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }
}


