package com.wafry.inventory.util;

import javafx.animation.FadeTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * NotificationUtil - Toast notification utility
 *
 * Displays temporary popup notifications to the user.
 *
 * @author Wafry Team
 */
public class NotificationUtil {
    private static final Logger logger = Logger.getLogger(NotificationUtil.class);

    private static final int NOTIFICATION_WIDTH = 300;
    private static final int NOTIFICATION_HEIGHT = 60;
    private static final Duration SHOW_DURATION = Duration.seconds(3);

    /**
     * Show success notification
     *
     * @param stage The stage to show notification on
     * @param message The message to display
     */
    public static void showSuccess(Stage stage, String message) {
        showNotification(stage, message, "success");
    }

    /**
     * Show error notification
     *
     * @param stage The stage to show notification on
     * @param message The message to display
     */
    public static void showError(Stage stage, String message) {
        showNotification(stage, message, "error");
    }

    /**
     * Show warning notification
     *
     * @param stage The stage to show notification on
     * @param message The message to display
     */
    public static void showWarning(Stage stage, String message) {
        showNotification(stage, message, "warning");
    }

    /**
     * Show info notification
     *
     * @param stage The stage to show notification on
     * @param message The message to display
     */
    public static void showInfo(Stage stage, String message) {
        showNotification(stage, message, "info");
    }

    /**
     * Show notification with specified type
     *
     * @param stage The stage to show notification on
     * @param message The message to display
     * @param type The notification type (success, error, warning, info)
     */
    private static void showNotification(Stage stage, String message, String type) {
        if (stage == null || message == null) {
            return;
        }

        logger.debug("Showing " + type + " notification: " + message);

        Label label = new Label(message);
        label.setWrapText(true);
        label.setStyle(getStyleForType(type));

        StackPane notificationPane = new StackPane(label);
        notificationPane.setStyle("-fx-padding: 15px; -fx-border-radius: 5px; -fx-background-color: " +
                                getBackgroundColorForType(type) + ";");
        notificationPane.setPrefWidth(NOTIFICATION_WIDTH);
        notificationPane.setPrefHeight(NOTIFICATION_HEIGHT);
        notificationPane.setOpacity(0.95);

        // Position at top-right
        try {
            Bounds bounds = stage.getScene().getRoot().getLayoutBounds();
            notificationPane.setLayoutX(bounds.getMaxX() - NOTIFICATION_WIDTH - 20);
            notificationPane.setLayoutY(20);
        } catch (Exception e) {
            logger.warn("Could not position notification");
        }

        // Add fade out animation
        FadeTransition fadeOut = new FadeTransition(SHOW_DURATION, notificationPane);
        fadeOut.setFromValue(0.95);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> {
            try {
                ((javafx.scene.layout.Pane) stage.getScene().getRoot()).getChildren().remove(notificationPane);
            } catch (Exception e) {
                logger.debug("Could not remove notification from pane");
            }
        });

        try {
            ((javafx.scene.layout.Pane) stage.getScene().getRoot()).getChildren().add(notificationPane);
            fadeOut.play();
        } catch (Exception e) {
            logger.warn("Could not display notification: " + e.getMessage());
        }
    }

    /**
     * Get CSS style for notification type
     */
    private static String getStyleForType(String type) {
        switch (type.toLowerCase()) {
            case "success":
                return "-fx-text-fill: #2e7d32; -fx-font-size: 12px;";
            case "error":
                return "-fx-text-fill: #c62828; -fx-font-size: 12px;";
            case "warning":
                return "-fx-text-fill: #f57f17; -fx-font-size: 12px;";
            case "info":
            default:
                return "-fx-text-fill: #0277bd; -fx-font-size: 12px;";
        }
    }

    /**
     * Get background color for notification type
     */
    private static String getBackgroundColorForType(String type) {
        switch (type.toLowerCase()) {
            case "success":
                return "#c8e6c9";
            case "error":
                return "#ffcdd2";
            case "warning":
                return "#ffe0b2";
            case "info":
            default:
                return "#b3e5fc";
        }
    }
}

