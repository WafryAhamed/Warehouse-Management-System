package com.orbytex.orbytex1;

import javafx.application.Application;
import javafx.stage.Stage;
import com.orbytex.orbytex1.util.SceneManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrbyteX1Application extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            log.info("Starting OrbyteX1 Application");

            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.setPrimaryStage(primaryStage);

            // Load login scene
            sceneManager.loadScene("/fxml/login.fxml", "OrbyteX1 - Login");

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

