package com.linkextractor.backend;

import com.linkextractor.backend.dto.LoginRequestDTO;
import com.linkextractor.backend.dto.LoginResponseDTO;
import com.linkextractor.backend.dto.RegistrationRequestDTO;
import com.linkextractor.backend.exceptions.InvalidLoginException;
import com.linkextractor.backend.exceptions.UserRegistrationException;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.service.AuthenticationService;
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
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    // Test for successful user registration
    @Test
    public void testRegisterUserEndpoint_Success() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("John", "Doe", "john@example.com", "johndoe", "password");
        when(authenticationService.registerUser(any(RegistrationRequestDTO.class))).thenReturn(new User());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Test for existing username during user registration
    @Test
    public void testRegisterUserEndpoint_ExistingUsername() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("John", "Doe", "john@example.com", "existinguser", "password");
        when(authenticationService.registerUser(any(RegistrationRequestDTO.class))).thenThrow(UserRegistrationException.class);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Test for existing email during user registration
    @Test
    public void testRegisterUserEndpoint_ExistingEmail() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("John", "Doe", "existing@hva.nl", "newuser", "password");
        when(authenticationService.registerUser(any(RegistrationRequestDTO.class))).thenThrow(UserRegistrationException.class);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Test for invalid user registration data
    @Test
    public void testRegisterUserEndpoint_InvalidData() throws Exception {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("", "", "", "", "");
        verify(authenticationService, never()).registerUser(any(RegistrationRequestDTO.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Test for successful user login
//    @Test
//    public void testLoginUserEndpoint_Success() throws Exception {
//        // Arrange
//        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("johndoe", "password");
//        when(authenticationService.loginUser(any(LoginRequestDTO.class))).thenReturn(new LoginResponseDTO("generatedAccesToken", "generatedRefreshToken"));
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(loginRequestDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.accesToken").exists());
//    }

    // Test for invalid user credentials during login
    @Test
    public void testLoginUserEndpoint_InvalidCredentials() throws Exception {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("invaliduser", "wrongpassword");
        when(authenticationService.loginUser(any(LoginRequestDTO.class))).thenThrow(InvalidLoginException.class);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

