package com.wafry.inventory.exception;

/**
 * ApiException - Exception for API communication errors
 *
 * @author Wafry Team
 */
public class ApiException extends RuntimeException {
    private final int httpStatusCode;
    private final String responseBody;

    public ApiException(String message) {
        super(message);
        this.httpStatusCode = -1;
        this.responseBody = null;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = -1;
        this.responseBody = null;
    }

    public ApiException(String message, int httpStatusCode, String responseBody) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.responseBody = responseBody;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "message='" + getMessage() + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}

