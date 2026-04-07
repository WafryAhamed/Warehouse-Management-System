package com.wafry.inventory.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 * DialogUtil - Dialog utility for user interactions
 *
 * Provides common dialog boxes for confirmations and input.
 *
 * @author Wafry Team
 */
public class DialogUtil {
    private static final Logger logger = Logger.getLogger(DialogUtil.class);

    /**
     * Show confirmation dialog
     *
     * @param title Dialog title
     * @param header Dialog header
     * @param message Dialog message
     * @return true if user clicked OK, false otherwise
     */
    public static boolean showConfirmation(String title, String header, String message) {
        logger.debug("Showing confirmation dialog: " + title);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        return alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .isPresent();
    }

    /**
     * Show information dialog
     *
     * @param title Dialog title
     * @param header Dialog header
     * @param message Dialog message
     */
    public static void showInformation(String title, String header, String message) {
        logger.debug("Showing information dialog: " + title);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show warning dialog
     *
     * @param title Dialog title
     * @param header Dialog header
     * @param message Dialog message
     */
    public static void showWarning(String title, String header, String message) {
        logger.warn("Showing warning dialog: " + title);

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show error dialog
     *
     * @param title Dialog title
     * @param header Dialog header
     * @param message Dialog message
     */
    public static void showError(String title, String header, String message) {
        logger.error("Showing error dialog: " + title);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show text input dialog
     *
     * @param title Dialog title
     * @param header Dialog header
     * @param message Input message
     * @param defaultValue Default value
     * @return User input or null if cancelled
     */
    public static String showInputDialog(String title, String header, String message, String defaultValue) {
        logger.debug("Showing input dialog: " + title);

        TextInputDialog dialog = new TextInputDialog(defaultValue != null ? defaultValue : "");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(message);

        return dialog.showAndWait().orElse(null);
    }

    /**
     * Show delete confirmation dialog
     *
     * @param itemName Name of item to delete
     * @return true if user confirmed deletion
     */
    public static boolean showDeleteConfirmation(String itemName) {
        return showConfirmation(
                "Confirm Deletion",
                "Delete " + itemName + "?",
                "Are you sure you want to delete " + itemName + "? This action cannot be undone."
        );
    }

    /**
     * Show unsaved changes confirmation
     *
     * @return true if user wants to proceed, false to cancel
     */
    public static boolean showUnsavedChangesConfirmation() {
        return showConfirmation(
                "Unsaved Changes",
                "Unsaved changes detected",
                "You have unsaved changes. Do you want to proceed without saving?"
        );
    }

    /**
     * Show success dialog
     *
     * @param title Dialog title
     * @param message Success message
     */
    public static void showSuccess(String title, String message) {
        showInformation(title, "Success", message);
    }

    /**
     * Show exception dialog
     *
     * @param title Dialog title
     * @param exception The exception to display
     */
    public static void showException(String title, Exception exception) {
        String message = exception.getMessage() != null ? exception.getMessage() : exception.toString();
        showError(title, "An error occurred", message);
        logger.error("Exception shown in dialog", exception);
    }
}

