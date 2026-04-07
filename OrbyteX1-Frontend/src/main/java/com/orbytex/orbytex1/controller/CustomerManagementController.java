package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerManagementController {
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
        com.orbytex.orbytex1.util.SceneManager.getInstance().loadScene("/fxml/login.fxml", "OrbyteX1 - Login");
    }
}

