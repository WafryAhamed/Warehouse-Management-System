package com.wafry.inventory.exception;

/**
 * AuthenticationException - Exception for authentication failures
 *
 * @author Wafry Team
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

