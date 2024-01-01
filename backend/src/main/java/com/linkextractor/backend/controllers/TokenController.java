package com.linkextractor.backend.controllers;

import com.linkextractor.backend.dto.TokenRefreshRequestDTO;
import com.linkextractor.backend.dto.TokenRefreshResponseDTO;
import com.linkextractor.backend.service.TokenService;
import com.linkextractor.backend.service.TokenBlackListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling token-related operations like token refresh.
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenBlackListService tokenBlackListService;

    private final TokenService tokenService;

    public TokenController(TokenBlackListService tokenBlackListService, TokenService tokenService) {
        this.tokenBlackListService = tokenBlackListService;
        this.tokenService = tokenService;
    }

    /**
     * Endpoint to refresh the access token using a valid refresh token.
     *
     * @param tokenRefreshRequestDTO Request containing the expired access token and refresh token.
     * @return ResponseEntity containing the new access token or an error message.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDTO tokenRefreshRequestDTO) {
        // Extract the expired access token and refresh token from the request
        String expiredAccessToken = tokenRefreshRequestDTO.getExpiredAccessToken();
        String refreshToken = tokenRefreshRequestDTO.getRefreshToken();

        // Validate if the refresh token is valid
        boolean isValid = tokenService.isValidRefreshToken(refreshToken);

        // Process the refresh token
        if (isValid) {
            // Check if the token is blacklisted
            boolean isBlacklisted = tokenBlackListService.isBlacklisted(refreshToken);

            // If the token is not blacklisted, proceed with generating a new access token
            if (!isBlacklisted) {
                // Extract the username from the valid refresh token
                String username = tokenService.extractUsernameFromToken(refreshToken);

                // Generate a new access token using the username
                String newAccessToken = tokenService.generateAccessTokenForRefresh(username);

                // Add the expired access token and refresh token to the blacklist
                tokenBlackListService.addToBlacklist(expiredAccessToken);
                tokenBlackListService.addToBlacklist(refreshToken);

                // Respond with the new access token
                return ResponseEntity.ok(new TokenRefreshResponseDTO(newAccessToken));
            } else {
                // If the refresh token is blacklisted, return an error response
                return ResponseEntity.badRequest().body("Refresh token is blacklisted");
            }
        } else {
            // If the refresh token is invalid, return an error response
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }
    }
}
