package com.wafry.inventory.exception;

/**
 * ValidationException - Exception for validation errors
 *
 * @author Wafry Team
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

