package com.wafry.inventory.controller;

import com.wafry.inventory.api.ApiClient;
import com.wafry.inventory.model.AuthResponse;
import com.wafry.inventory.util.SceneManager;
import com.wafry.inventory.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        // Allow Enter key to submit login
        passwordField.setOnKeyPressed(this::handleKeyPress);
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    @FXML
    private void onLoginButtonClick() {
        handleLogin();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Validation Error", "Please enter both username and password");
            return;
        }

        try {
            log.info("Attempting login for user: {}", username);
            AuthResponse response = ApiClient.login(username, password);

            if (response != null && response.getUser() != null) {
                SessionManager.getInstance().setSession(response.getToken(), response.getUser());
                log.info("Login successful for user: {}", username);

                // Navigate to dashboard
                navigateToDashboard();
            } else {
                showError("Login Failed", "Invalid response from server");
            }
        } catch (Exception e) {
            log.error("Login error", e);
            showError("Login Failed", "Invalid username or password");
        }
    }

    private void navigateToDashboard() {
        String role = SessionManager.getInstance().getUserRole();
        SceneManager sceneManager = SceneManager.getInstance();

        switch (role) {
            case "SUPER_ADMIN":
            case "ADMIN":
                sceneManager.loadScene("admin-dashboard.fxml", "Inventory - Admin Dashboard");
                break;
            case "MANAGER":
                sceneManager.loadScene("manager-dashboard.fxml", "Inventory - Manager Dashboard");
                break;
            case "CASHIER":
                sceneManager.loadScene("pos-dashboard.fxml", "Inventory - POS");
                break;
            case "INVENTORY_MANAGER":
                sceneManager.loadScene("inventory-dashboard.fxml", "Inventory - Inventory");
                break;
            default:
                sceneManager.loadScene("user-dashboard.fxml", "Inventory - Dashboard");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleDemoLogin() {
        usernameField.setText("admin");
        passwordField.setText("password");
        handleLogin();
    }
}

