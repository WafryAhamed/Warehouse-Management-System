package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderManagementController {
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
        com.orbytex.orbytex1.util.SceneManager.getInstance().loadScene("/fxml/login.fxml", "OrbyteX1 - Login");
    }
}

