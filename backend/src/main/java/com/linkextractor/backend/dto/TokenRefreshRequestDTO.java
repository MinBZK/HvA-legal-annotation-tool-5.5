package com.linkextractor.backend.dto;

/**
 * Data Transfer Object (DTO) representing the request to refresh an access token.
 */
public class TokenRefreshRequestDTO {
    private String expiredAccessToken;
    private String refreshToken;

    public TokenRefreshRequestDTO(String expiredAccessToken, String refreshToken) {
        this.expiredAccessToken = expiredAccessToken;
        this.refreshToken = refreshToken;
    }

    public String getExpiredAccessToken() {
        return expiredAccessToken;
    }

    public void setExpiredAccessToken(String expiredAccesToken) {
        this.expiredAccessToken = expiredAccesToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
