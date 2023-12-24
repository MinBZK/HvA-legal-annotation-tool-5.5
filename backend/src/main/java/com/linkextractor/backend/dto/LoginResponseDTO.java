package com.linkextractor.backend.dto;

/**
 * Data Transfer Object (DTO) representing a login response containing access and refresh tokens.
 */
public class LoginResponseDTO {
    private String accesToken;

    private String refreshToken;

    public LoginResponseDTO(String accesToken, String refreshToken) {
        this.accesToken = accesToken;
        this.refreshToken = refreshToken;
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
