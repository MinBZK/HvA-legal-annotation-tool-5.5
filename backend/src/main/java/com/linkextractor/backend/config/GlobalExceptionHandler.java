package com.linkextractor.backend.config;

import com.linkextractor.backend.exceptions.InvalidLoginException;
import com.linkextractor.backend.exceptions.UserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to manage and handle specific exceptions thrown across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserRegistrationException and returns a ResponseEntity with a bad request status and the exception message.
     *
     * @param ex UserRegistrationException instance
     * @return ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handleUserRegistrationException(UserRegistrationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles InvalidLoginException and returns a ResponseEntity with an unauthorized status and the exception message.
     *
     * @param ex InvalidLoginException instance
     * @return ResponseEntity with an unauthorized status and the exception message
     */
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> handleInvalidLoginException(InvalidLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
