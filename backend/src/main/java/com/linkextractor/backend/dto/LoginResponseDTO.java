package com.linkextractor.backend.dto;

/**
 * Data Transfer Object (DTO) representing a login response containing a JWT token.
 */
public class LoginResponseDTO {
    private String jwt;

    public LoginResponseDTO(String jwt){
        this.jwt = jwt;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}
