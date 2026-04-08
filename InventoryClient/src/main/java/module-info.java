module com.wafry.inventory {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.prefs;

    // Logging
    requires org.slf4j;
    requires ch.qos.logback.classic;

    // HTTP & JSON
    requires com.google.gson;
    requires okhttp3;

    // UI components
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    // Open packages to JavaFX for reflection
    opens com.wafry.inventory.app to javafx.fxml;
    opens com.wafry.inventory.controller to javafx.fxml;

    // Open packages to GSON for reflection
    opens com.wafry.inventory.api to com.google.gson;
    opens com.wafry.inventory.model to com.google.gson;

    // Export main packages
    exports com.wafry.inventory.app;
    exports com.wafry.inventory.controller;
    exports com.wafry.inventory.service;
    exports com.wafry.inventory.model;
    exports com.wafry.inventory.api;
    exports com.wafry.inventory.util;
}