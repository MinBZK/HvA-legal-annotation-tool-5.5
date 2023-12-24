package com.linkextractor.backend.dto;

/**
 * Data Transfer Object (DTO) representing the response containing a refreshed access token.
 */
public class TokenRefreshResponseDTO {

    private String accessToken;

    public TokenRefreshResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
