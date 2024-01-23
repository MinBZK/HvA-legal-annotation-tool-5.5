package com.linkextractor.backend.controllers;

import com.linkextractor.backend.dto.TokenRefreshRequestDTO;
import com.linkextractor.backend.service.TokenBlackListService;
import com.linkextractor.backend.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenBlackListService tokenBlackListService;

    @MockBean
    private TokenService tokenService;

    /**
     * Test for successful token refresh.
     */
    @Test
    public void testRefreshToken_Success() throws Exception {
        // Arrange
        TokenRefreshRequestDTO tokenRefreshRequestDTO = new TokenRefreshRequestDTO("expiredAccessToken", "validRefreshToken");
        when(tokenService.isValidRefreshToken(any(String.class))).thenReturn(true);
        when(tokenBlackListService.isBlacklisted(any(String.class))).thenReturn(false);
        when(tokenService.extractUsernameFromToken(any(String.class))).thenReturn("testUser");
        when(tokenService.generateAccessTokenForRefresh(any(String.class))).thenReturn("newAccessToken");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tokenRefreshRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").exists());
    }

    /**
     * Test for invalid refresh token during token refresh.
     */
    @Test
    public void testRefreshToken_InvalidRefreshToken() throws Exception {
        // Arrange
        TokenRefreshRequestDTO tokenRefreshRequestDTO = new TokenRefreshRequestDTO("expiredAccessToken", "invalidRefreshToken");
        when(tokenService.isValidRefreshToken(any(String.class))).thenReturn(false);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tokenRefreshRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid refresh token"));
    }

    /**
     * Test for blacklisted refresh token during token refresh.
     */
    @Test
    public void testRefreshToken_BlacklistedRefreshToken() throws Exception {
        // Arrange
        TokenRefreshRequestDTO tokenRefreshRequestDTO = new TokenRefreshRequestDTO("expiredAccessToken", "blacklistedRefreshToken");
        when(tokenService.isValidRefreshToken(any(String.class))).thenReturn(true);
        when(tokenBlackListService.isBlacklisted(any(String.class))).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tokenRefreshRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Refresh token is blacklisted"));
    }

    /**
     * Helper method to convert object to JSON string.
     */
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
