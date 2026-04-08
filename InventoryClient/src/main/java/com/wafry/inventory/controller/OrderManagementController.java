package com.wafry.inventory.controller;

import com.wafry.inventory.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderManagementController {
    private static final Logger log = LoggerFactory.getLogger(OrderManagementController.class);
    @FXML
    private Label userNameLabel;

    @FXML
    private TableView<?> ordersTable;

    @FXML
    private Button createOrderButton;

    @FXML
    private Button viewDetailsButton;

    @FXML
    private void initialize() {
        var currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            userNameLabel.setText(currentUser.getFullName());
        }
        loadOrders();
    }

    private void loadOrders() {
        log.info("Loading orders...");
        // TODO: Fetch orders from API
    }

    @FXML
    private void handleCreateOrder() {
        log.info("Creating new order");
        // TODO: Open create order dialog
    }

    @FXML
    private void handleViewDetails() {
        log.info("Viewing order details");
        // TODO: Show order details
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().clearSession();
        com.wafry.inventory.util.SceneManager.getInstance().loadScene("/fxml/login.fxml", "OrbyteX1 - Login");
    }
}


