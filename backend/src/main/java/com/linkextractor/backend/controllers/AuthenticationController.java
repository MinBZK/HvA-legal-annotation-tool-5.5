package com.linkextractor.backend.controllers;

import com.linkextractor.backend.dto.LoginRequestDTO;
import com.linkextractor.backend.dto.LogoutResponseDTO;
import com.linkextractor.backend.dto.RegistrationRequestDTO;
import com.linkextractor.backend.exceptions.InvalidLoginException;
import com.linkextractor.backend.exceptions.UserRegistrationException;
import com.linkextractor.backend.service.AuthenticationService;

import com.linkextractor.backend.service.TokenBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user authentication operations like registration, login, and logout.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    /**
     * Endpoint to register a new user.
     *
     * @param registrationRequestDTO Data for user registration.
     * @return ResponseEntity with the result of the registration process.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        if (registrationRequestDTO == null ||
                isNullOrEmpty(registrationRequestDTO.getUsername()) ||
                isNullOrEmpty(registrationRequestDTO.getEmail()) ||
                isNullOrEmpty(registrationRequestDTO.getPassword()) ||
                isNullOrEmpty(registrationRequestDTO.getFirstname()) ||
                isNullOrEmpty(registrationRequestDTO.getLastname())) {
            throw new UserRegistrationException("Invalid Registration Data");
        }

        return ResponseEntity.ok(authenticationService.registerUser(registrationRequestDTO));
    }

    /**
     * Endpoint to authenticate a user login.
     *
     * @param loginRequestDTO Data for user login.
     * @return ResponseEntity with the result of the login process.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        if (loginRequestDTO == null || isNullOrEmpty(loginRequestDTO.getUsername()) || isNullOrEmpty(loginRequestDTO.getPassword())) {
            throw new InvalidLoginException("Invalid Login Data");
        }

        return ResponseEntity.ok(authenticationService.loginUser(loginRequestDTO));
    }

    /**
     * Endpoint to log out a user.
     *
     * @param logoutResponseDTO Data for user logout containing access and refresh tokens.
     * @return ResponseEntity indicating successful logout.
     */
    @PostMapping("logout")
    public ResponseEntity<String> logoutUser(@RequestBody LogoutResponseDTO logoutResponseDTO) {
        // Add access and refresh tokens to the blacklist for logout
        tokenBlackListService.addToBlacklist(logoutResponseDTO.getAccesToken());
        tokenBlackListService.addToBlacklist(logoutResponseDTO.getRefreshToken());

        return ResponseEntity.ok("Logged out successfully");
    }

    // Helper method to check if a string is null or empty
    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
