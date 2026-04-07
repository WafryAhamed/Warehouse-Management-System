package com.wafry.inventory.app;

import javafx.application.Application;

/**
 * Launcher - Simple entry point for the application
 * Delegates to InventoryApplication
 *
 * @author Wafry Team
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(InventoryApplication.class, args);
    }
}

