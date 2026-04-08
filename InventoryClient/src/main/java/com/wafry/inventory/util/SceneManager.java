package com.wafry.inventory.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;

public class SceneManager {
    private static final Logger log = LoggerFactory.getLogger(SceneManager.class);
    private static SceneManager instance;
    private static Stage staticPrimaryStage;
    private Stage primaryStage;
    private Scene currentScene;

    private SceneManager() {}

    public static synchronized SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    // Static method for initialization from application
    public static void initialize(Stage stage) {
        staticPrimaryStage = stage;
        getInstance().setPrimaryStage(stage);
        log.info("SceneManager initialized with primary stage");
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    // Overloaded method to support both 1 and 2 parameter calls
    public static void loadScene(String fxmlPath) {
        getInstance().loadSceneInternal(fxmlPath, null);
    }

    public void loadScene(String fxmlPath, String title) {
        loadSceneInternal(fxmlPath, title);
    }

    private void loadSceneInternal(String fxmlPath, String title) {
        try {
            Stage stage = primaryStage != null ? primaryStage : staticPrimaryStage;
            // Construct full path to FXML file
            String fullPath = "/com/wafry/inventory/fxml/" + fxmlPath;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fullPath));
            if (loader.getLocation() == null) {
                throw new IOException("FXML file not found: " + fullPath);
            }
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Try to load CSS if it exists
            try {
                URL cssResource = getClass().getResource("/styles/style.css");
                if (cssResource != null) {
                    scene.getStylesheets().add(cssResource.toExternalForm());
                }
            } catch (Exception e) {
                log.warn("Could not load CSS stylesheet: " + e.getMessage());
            }

            stage.setScene(scene);
            if (title != null) {
                stage.setTitle(title);
            }
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

