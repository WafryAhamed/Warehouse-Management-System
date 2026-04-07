package com.wafry.inventory.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger - Centralized logging utility
 *
 * Provides consistent logging across the application with timestamp and level information.
 *
 * @author Wafry Team
 */
public class Logger {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private final Class<?> clazz;

    private Logger(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

    public void debug(String message) {
        log("DEBUG", message);
    }

    public void debug(String message, Throwable throwable) {
        log("DEBUG", message);
        throwable.printStackTrace();
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void info(String message, Throwable throwable) {
        log("INFO", message);
        throwable.printStackTrace();
    }

    public void warn(String message) {
        log("WARN", message);
    }

    public void warn(String message, Throwable throwable) {
        log("WARN", message);
        throwable.printStackTrace();
    }

    public void error(String message) {
        log("ERROR", message);
    }

    public void error(String message, Throwable throwable) {
        log("ERROR", message);
        throwable.printStackTrace();
    }

    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String className = clazz.getSimpleName();
        System.out.printf("[%s] [%s] [%s] %s%n", timestamp, level, className, message);
    }
}

