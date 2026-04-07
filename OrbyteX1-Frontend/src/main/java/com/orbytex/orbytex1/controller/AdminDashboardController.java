package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.api.ApiClient;
import com.orbytex.orbytex1.model.DashboardStats;
import com.orbytex.orbytex1.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import java.text.DecimalFormat;

@Slf4j
public class AdminDashboardController {
    @FXML
    private Label userFullNameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private Label totalRevenueLabel;

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private Label totalCustomersLabel;

    @FXML
    private Label totalProductsLabel;

    @FXML
    private Label lowStockLabel;

    @FXML
    private Label netProfitLabel;

    @FXML
    private Label activeUsersLabel;

    @FXML
    private ProgressIndicator loadingIndicator;

    private DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    private void initialize() {
        // Set user info
        var currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            userFullNameLabel.setText(currentUser.getFullName());
            userRoleLabel.setText(currentUser.getRoleName());
        }

        // Load dashboard stats
        loadDashboardStats();
    }

    private void loadDashboardStats() {
        loadingIndicator.setVisible(true);

        new Thread(() -> {
            try {
                DashboardStats stats = ApiClient.getDashboardStats();

                javafx.application.Platform.runLater(() -> {
                    totalRevenueLabel.setText("₹" + df.format(stats.getTotalRevenue()));
                    totalOrdersLabel.setText(stats.getTotalOrders().toString());
                    totalCustomersLabel.setText(stats.getTotalCustomers().toString());
                    totalProductsLabel.setText(stats.getTotalProducts().toString());
                    lowStockLabel.setText(stats.getLowStockProducts().toString());
                    netProfitLabel.setText("₹" + df.format(stats.getNetProfit()));
                    activeUsersLabel.setText(stats.getActiveUsers().toString());

                    loadingIndicator.setVisible(false);
                    log.info("Dashboard stats loaded successfully");
                });
            } catch (Exception e) {
                log.error("Error loading dashboard stats", e);
                javafx.application.Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                });
            }
        }).start();
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().clearSession();
        com.orbytex.orbytex1.util.SceneManager.getInstance().loadScene("/fxml/login.fxml", "OrbyteX1 - Login");
    }
}

