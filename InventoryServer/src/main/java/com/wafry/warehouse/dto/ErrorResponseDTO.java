package com.wafry.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
    private String message;
    private String error;
    private String errorCode;
    private Integer status;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponseDTO() {}

    public ErrorResponseDTO(String message, String errorCode, LocalDateTime timestamp, String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
        this.path = path;
    }

    public static ErrorResponseDTOBuilder builder() {
        return new ErrorResponseDTOBuilder();
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public static class ErrorResponseDTOBuilder {
        private String message;
        private String error;
        private String errorCode;
        private Integer status;
        private LocalDateTime timestamp;
        private String path;

        public ErrorResponseDTOBuilder message(String message) { this.message = message; return this; }
        public ErrorResponseDTOBuilder error(String error) { this.error = error; return this; }
        public ErrorResponseDTOBuilder errorCode(String errorCode) { this.errorCode = errorCode; return this; }
        public ErrorResponseDTOBuilder status(Integer status) { this.status = status; return this; }
        public ErrorResponseDTOBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public ErrorResponseDTOBuilder path(String path) { this.path = path; return this; }

        public ErrorResponseDTO build() {
            ErrorResponseDTO dto = new ErrorResponseDTO();
            dto.message = this.message;
            dto.error = this.error;
            dto.errorCode = this.errorCode;
            dto.status = this.status;
            dto.timestamp = this.timestamp;
            dto.path = this.path;
            return dto;
        }
    }

}

