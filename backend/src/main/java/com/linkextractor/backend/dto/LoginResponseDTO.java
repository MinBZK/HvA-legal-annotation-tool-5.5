package com.linkextractor.backend.dto;

/**
 * Data Transfer Object (DTO) representing a login response containing access and refresh tokens.
 */
public class LoginResponseDTO {
    private String username;

    private String accesToken;

    private String refreshToken;

    public LoginResponseDTO(String username, String accesToken, String refreshToken) {
        this.username = username;
        this.accesToken = accesToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
