package com.wafry.inventory.controller;

import com.wafry.inventory.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupplierManagementController {
    private static final Logger log = LoggerFactory.getLogger(SupplierManagementController.class);

    @FXML
    private Label userNameLabel;

    @FXML
    private TableView<?> suppliersTable;

    @FXML
    private Button addSupplierButton;

    @FXML
    private Button editSupplierButton;

    @FXML
    private void initialize() {
        var currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            userNameLabel.setText(currentUser.getFullName());
        }
        loadSuppliers();
    }

    private void loadSuppliers() {
        log.info("Loading suppliers...");
        // TODO: Fetch suppliers from API
    }

    @FXML
    private void handleAddSupplier() {
        log.info("Adding new supplier");
        // TODO: Open add supplier dialog
    }

    @FXML
    private void handleEditSupplier() {
        log.info("Editing supplier");
        // TODO: Open edit supplier dialog
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().clearSession();
        com.wafry.inventory.util.SceneManager.getInstance().loadScene("/fxml/login.fxml", "OrbyteX1 - Login");
    }
}

