package com.linkextractor.backend.exceptions;

/**
 * Custom exception class for handling user registration-related errors.
 * This exception is thrown when there's an issue with user registration.
 */
public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message) {
        super(message);
    }
}
