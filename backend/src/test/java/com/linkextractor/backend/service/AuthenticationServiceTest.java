package com.linkextractor.backend.service;

import com.linkextractor.backend.dto.LoginRequestDTO;
import com.linkextractor.backend.dto.LoginResponseDTO;
import com.linkextractor.backend.dto.RegistrationRequestDTO;
import com.linkextractor.backend.exceptions.InvalidLoginException;
import com.linkextractor.backend.exceptions.UserRegistrationException;
import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.respositories.RoleRepository;
import com.linkextractor.backend.respositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationService authenticationService;

    // Constants for mock values
    private static final String MOCK_ACCESS_TOKEN = "mockAccessToken";
    private static final String MOCK_REFRESH_TOKEN = "mockRefreshToken";


    /**
     * Tests the successful registration of a user.
     */
    @Test
    public void testRegisterUser_Success() {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = createRegistrationDTO();
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(roleRepository.findByAuthority(any())).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(new User());

        // Act
        User registeredUser = authenticationService.registerUser(registrationRequestDTO);

        // Assert
        assertNotNull(registeredUser);

        // Additional assertions to check values of RegistrationRequestDTO
        assertEquals("username", registrationRequestDTO.getUsername());
        assertEquals("password", registrationRequestDTO.getPassword());
        assertEquals("email@hva.nl", registrationRequestDTO.getEmail());
        assertEquals("John", registrationRequestDTO.getFirstname());
        assertEquals("Doe", registrationRequestDTO.getLastname());
    }

    /**
     * Tests the scenario where the username already exists during registration.
     */
    @Test
    public void testRegisterUser_UsernameExists() {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = createRegistrationDTO();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        // Act and Assert
        assertThrows(UserRegistrationException.class, () -> authenticationService.registerUser(registrationRequestDTO));
    }

    /**
     * Tests the scenario where the email already exists during registration.
     */
    @Test
    public void testRegisterUser_EmailExists() {
        // Arrange
        RegistrationRequestDTO registrationRequestDTO = createRegistrationDTO();
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        // Act and Assert
        assertThrows(UserRegistrationException.class, () -> authenticationService.registerUser(registrationRequestDTO));
    }

    /**
     * Tests the successful login of a user.
     */
    @Test
    public void testLoginUser_Success() {
        // Arrange
        LoginRequestDTO loginRequestDTO = createLoginDTO();
        when(authenticationManager.authenticate(any())).thenReturn(Mockito.mock(org.springframework.security.core.Authentication.class));
        when(tokenService.generateAccessToken(any())).thenReturn(MOCK_ACCESS_TOKEN);
        when(tokenService.generateRefreshToken(any())).thenReturn(MOCK_REFRESH_TOKEN);

        // Act
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(loginRequestDTO);

        // Assert
        assertNotNull(loginResponseDTO);
        assertEquals(MOCK_ACCESS_TOKEN, loginResponseDTO.getAccesToken());
        assertEquals(MOCK_REFRESH_TOKEN, loginResponseDTO.getRefreshToken());

        // Additional assertions to check values of LoginRequestDTO
        assertEquals("username", loginRequestDTO.getUsername());
        assertEquals("password", loginRequestDTO.getPassword());
    }

    /**
     * Tests the scenario where an invalid login attempt is made.
     */
    @Test
    public void testLoginUser_InvalidLogin() {
        // Arrange
        LoginRequestDTO loginRequestDTO = createLoginDTO();
        when(authenticationManager.authenticate(any())).thenThrow(new InvalidLoginException("Invalid Login Details"));

        // Act and Assert
        assertThrows(InvalidLoginException.class, () -> authenticationService.loginUser(loginRequestDTO));
    }

    /**
     * Helper method to create a {@link RegistrationRequestDTO} for testing.
     */
    private RegistrationRequestDTO createRegistrationDTO() {
        return new RegistrationRequestDTO(
                "John",
                "Doe",
                "email@hva.nl",
                "username",
                "password"
        );
    }

    /**
     * Helper method to create a {@link LoginRequestDTO} for testing.
     */
    private LoginRequestDTO createLoginDTO() {
        return new LoginRequestDTO(
                "username",
                "password"
        );
    }
}
