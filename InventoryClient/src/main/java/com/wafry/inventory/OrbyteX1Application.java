package com.wafry.inventory;

import javafx.application.Application;
import javafx.stage.Stage;
import com.wafry.inventory.util.SceneManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrbyteX1Application extends Application {
    private static final Logger log = LoggerFactory.getLogger(OrbyteX1Application.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            log.info("Starting Inventory Application");

            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.setPrimaryStage(primaryStage);

            // Load login scene
            sceneManager.loadScene("/fxml/login.fxml", "Inventory Management System - Login");

            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            primaryStage.show();

            log.info("Application started successfully");
        } catch (Exception e) {
            log.error("Error starting application", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

