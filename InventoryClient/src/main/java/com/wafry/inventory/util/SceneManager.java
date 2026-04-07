package com.wafry.inventory.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.wafry.inventory.config.ApplicationConfig;

/**
 * SceneManager - Centralized scene and navigation management
 *
 * Responsibilities:
 * - Load FXML files and manage scenes
 * - Handle window/stage management
 * - Apply styling (CSS)
 * - Provide navigation between scenes
 *
 * @author Wafry Team
 */
public class SceneManager {
    private static final Logger logger = Logger.getLogger(SceneManager.class);
    private static Stage primaryStage;

    /**
     * Initialize SceneManager with the primary stage
     *
     * @param stage The primary JavaFX stage
     */
    public static void initialize(Stage stage) {
        primaryStage = stage;
        logger.info("SceneManager initialized with primary stage");
    }

    /**
     * Load a scene from an FXML file
     *
     * @param fxmlFileName The FXML file name (e.g., "login-view.fxml")
     * @throws IllegalStateException If primaryStage is not initialized
     */
    public static void loadScene(String fxmlFileName) {
        if (primaryStage == null) {
            throw new IllegalStateException("SceneManager must be initialized first");
        }
        loadScene(fxmlFileName, primaryStage);
    }

    /**
     * Load a scene on a specific stage
     *
     * @param fxmlFileName The FXML file name
     * @param stage The stage to load the scene on
     */
    public static void loadScene(String fxmlFileName, Stage stage) {
        try {
            String resourcePath = ApplicationConfig.FXML_RESOURCE_PATH + fxmlFileName;
            logger.info(">>> SceneManager.loadScene() called");
            logger.info("FXML filename: " + fxmlFileName);
            logger.info("Full resource path: " + resourcePath);

            // Get the resource URL
            var resourceUrl = SceneManager.class.getResource(resourcePath);

            if (resourceUrl == null) {
                logger.error("FAILED: Resource not found!");
                logger.error("Tried to load from: " + resourcePath);
                logger.error("ClassLoader: " + SceneManager.class.getClassLoader());

                // Try to list available resources
                logger.warn("Attempting alternative resource paths...");
                var altPath1 = "/com/wafry/inventory/fxml/" + fxmlFileName;
                var altUrl1 = SceneManager.class.getResource(altPath1);
                logger.info("Alternative path 1: " + altPath1 + " -> " + (altUrl1 != null ? "FOUND" : "NOT FOUND"));

                throw new IllegalArgumentException("FXML file not found: " + resourcePath);
            }

            logger.info("Resource found: " + resourceUrl);
            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            logger.debug("FXMLLoader created");

            Object rootObj = fxmlLoader.load();
            logger.debug("FXML loaded successfully, root type: " + (rootObj != null ? rootObj.getClass().getSimpleName() : "null"));

            if (rootObj == null) {
                throw new RuntimeException("FXML root object is null");
            }

            // Cast to Parent for Scene constructor
            javafx.scene.Parent root = (javafx.scene.Parent) rootObj;

            Scene scene = new Scene(
                    root,
                    ApplicationConfig.DEFAULT_WINDOW_WIDTH,
                    ApplicationConfig.DEFAULT_WINDOW_HEIGHT
            );

            // Apply CSS styling
            applyStyling(scene);

            stage.setScene(scene);
            logger.info(">>> Scene loaded successfully: " + fxmlFileName);

        } catch (Exception e) {
            logger.error(">>> FAILED to load scene: " + fxmlFileName);
            logger.error("Exception: " + e.getClass().getName() + " - " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: " + e.getCause().getClass().getName() + " - " + e.getCause().getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to load scene: " + fxmlFileName, e);
        }
    }

    /**
     * Get the primary stage
     *
     * @return The primary JavaFX stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Apply CSS styling to a scene
     *
     * @param scene The scene to style
     */
    private static void applyStyling(Scene scene) {
        try {
            String cssResource = SceneManager.class.getResource(ApplicationConfig.CSS_RESOURCE_PATH)
                    .toExternalForm();
            scene.getStylesheets().add(cssResource);
            logger.debug("CSS styling applied");
        } catch (NullPointerException e) {
            logger.warn("CSS file not found - using default styling");
        } catch (Exception e) {
            logger.warn("Error loading CSS styling: " + e.getMessage());
        }
    }
}

