package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SupplierManagementController {
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
        com.orbytex.orbytex1.util.SceneManager.getInstance().loadScene("/fxml/login.fxml", "OrbyteX1 - Login");
    }
}

