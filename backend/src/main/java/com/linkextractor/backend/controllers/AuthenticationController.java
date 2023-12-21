package com.linkextractor.backend.controllers;

import com.linkextractor.backend.dto.LoginDTO;
import com.linkextractor.backend.dto.RegistrationDTO;
import com.linkextractor.backend.exceptions.InvalidLoginException;
import com.linkextractor.backend.exceptions.UserRegistrationException;
import com.linkextractor.backend.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication operations like registration and login.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint to register a new user.
     *
     * @param registrationDTO Data for user registration.
     * @return ResponseEntity with the result of the registration process.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO) {
        if (registrationDTO == null ||
                isNullOrEmpty(registrationDTO.getUsername()) ||
                isNullOrEmpty(registrationDTO.getEmail()) ||
                isNullOrEmpty(registrationDTO.getPassword()) ||
                isNullOrEmpty(registrationDTO.getFirstname()) ||
                isNullOrEmpty(registrationDTO.getLastname())) {
            throw new UserRegistrationException("Invalid Registration Data");
        }

        return ResponseEntity.ok(authenticationService.registerUser(registrationDTO));
    }

    /**
     * Endpoint to authenticate a user login.
     *
     * @param loginDTO Data for user login.
     * @return ResponseEntity with the result of the login process.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        if (loginDTO == null || isNullOrEmpty(loginDTO.getUsername()) || isNullOrEmpty(loginDTO.getPassword())) {
            throw new InvalidLoginException("Invalid Login Data");
        }

        return ResponseEntity.ok(authenticationService.loginUser(loginDTO));
    }

    // Helper method to check if a string is null or empty
    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
