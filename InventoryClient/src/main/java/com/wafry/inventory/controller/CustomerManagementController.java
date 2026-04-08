package com.wafry.inventory.controller;

import com.wafry.inventory.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerManagementController {
    private static final Logger log = LoggerFactory.getLogger(CustomerManagementController.class);
    @FXML
    private Label userNameLabel;

    @FXML
    private TableView<?> customersTable;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button editCustomerButton;

    @FXML
    private void initialize() {
        var currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            userNameLabel.setText(currentUser.getFullName());
        }
        loadCustomers();
    }

    private void loadCustomers() {
        log.info("Loading customers...");
        // TODO: Fetch customers from API
    }

    @FXML
    private void handleAddCustomer() {
        log.info("Adding new customer");
        // TODO: Open add customer dialog
    }

    @FXML
    private void handleEditCustomer() {
        log.info("Editing customer");
        // TODO: Open edit customer dialog
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().clearSession();
        com.wafry.inventory.util.SceneManager.getInstance().loadScene("/fxml/login.fxml", "OrbyteX1 - Login");
    }
}


