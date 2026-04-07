package com.wafry.inventory.app;

import javafx.application.Application;
import javafx.stage.Stage;
import com.wafry.inventory.config.ApplicationConfig;
import com.wafry.inventory.util.SceneManager;
import com.wafry.inventory.util.Logger;

/**
 * InventoryApplication - Main JavaFX Application Entry Point
 *
 * Responsibilities:
 * - Initialize the application
 * - Load configuration
 * - Setup primary stage and launch UI
 *
 * @author Wafry Team
 * @version 2.0
 */
public class InventoryApplication extends Application {

    private static final Logger logger = Logger.getLogger(InventoryApplication.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize application configuration
            ApplicationConfig.initialize();
            logger.info("Application configuration initialized");

            // Initialize scene manager
            SceneManager.initialize(primaryStage);
            logger.info("Scene manager initialized");

            // Load login view as startup screen
            SceneManager.loadScene("login-view.fxml");

            // Configure primary stage
            primaryStage.setTitle(ApplicationConfig.APP_TITLE);
            primaryStage.setMinWidth(ApplicationConfig.MIN_WINDOW_WIDTH);
            primaryStage.setMinHeight(ApplicationConfig.MIN_WINDOW_HEIGHT);
            primaryStage.centerOnScreen();

            primaryStage.show();

            logger.info("================================");
            logger.info("Inventory Management System");
            logger.info("Version: " + ApplicationConfig.APP_VERSION);
            logger.info("Status: ✅ Started successfully");
            logger.info("================================");

        } catch (Exception e) {
            logger.error("Failed to start application", e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

