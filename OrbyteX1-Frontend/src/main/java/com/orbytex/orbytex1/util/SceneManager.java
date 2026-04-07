package com.orbytex.orbytex1.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private Scene currentScene;

    private SceneManager() {}

    public static synchronized SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            currentScene = scene;
            log.info("Scene loaded: {}", fxmlPath);
        } catch (IOException e) {
            log.error("Error loading FXML: " + fxmlPath, e);
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}

