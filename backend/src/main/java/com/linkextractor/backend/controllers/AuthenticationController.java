package com.linkextractor.backend.controllers;

import com.linkextractor.backend.dto.LoginDTO;
import com.linkextractor.backend.dto.RegistrationDTO;
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
        return ResponseEntity.ok(authenticationService.loginUser(loginDTO));
    }
}
