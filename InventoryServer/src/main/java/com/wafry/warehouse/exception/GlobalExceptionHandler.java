package com.wafry.warehouse.exception;

import com.wafry.warehouse.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.LocalDateTime;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .errorCode("NOT_FOUND")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(ValidationException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .errorCode("VALIDATION_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .errorCode("UNAUTHORIZED")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(ForbiddenException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .errorCode("FORBIDDEN")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicate(DuplicateResourceException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .errorCode("CONFLICT")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message("An unexpected error occurred")
                .errorCode("INTERNAL_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message("Data integrity error: Duplicate or invalid data")
                .errorCode("DATA_INTEGRITY_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataAccessException(DataAccessException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message("Database access error: Unable to access database")
                .errorCode("DATABASE_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponseDTO> handleSQLException(SQLException ex, WebRequest request) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message("Database error: SQL execution failed")
                .errorCode("SQL_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


