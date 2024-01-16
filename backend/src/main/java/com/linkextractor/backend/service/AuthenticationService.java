package com.linkextractor.backend.service;

import java.util.HashSet;
import java.util.Set;

import com.linkextractor.backend.exceptions.InvalidLoginException;
import com.linkextractor.backend.exceptions.UserRegistrationException;
import com.linkextractor.backend.dto.LoginRequestDTO;
import com.linkextractor.backend.dto.RegistrationRequestDTO;
import com.linkextractor.backend.dto.LoginResponseDTO;
import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.respositories.RoleRepository;
import com.linkextractor.backend.respositories.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service handling user authentication and registration.
 */
@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Registers a new user based on the provided registration information.
     *
     * @param registrationRequestDTO The RegistrationDTO containing user registration details.
     * @return The newly registered User object.
     * @throws UserRegistrationException if username or email already exists.
     */
    public User registerUser(RegistrationRequestDTO registrationRequestDTO) {
        // Check if username or email already exist in the database
        if (userRepository.findByUsername(registrationRequestDTO.getUsername()).isPresent()) {
            throw new UserRegistrationException("Username already exists");
        }

        if (userRepository.findByEmail(registrationRequestDTO.getEmail()).isPresent()) {
            throw new UserRegistrationException("Email already exists");
        }

        // Encode the password before saving it to the database
        String encodedPassword = passwordEncoder.encode(registrationRequestDTO.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        // Create and save the new User object
        return userRepository.save(new User(0, registrationRequestDTO.getUsername(), encodedPassword, registrationRequestDTO.getEmail(), registrationRequestDTO.getFirstname(), registrationRequestDTO.getLastname(), authorities));
    }

    /**
     * Authenticates a user based on the provided login information.
     *
     * @param loginRequestDTO The LoginDTO containing user login details.
     * @return The LoginResponseDTO with user details and authentication token upon successful authentication.
     * @throws InvalidLoginException if authentication fails with invalid login details.
     */
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        try {
            // Authenticate user credentials using AuthenticationManager
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
            );

            String username = loginRequestDTO.getUsername();

            // Generate JWT token for the authenticated user
            String token = tokenService.generateAccessToken(auth);

            // Generate Refresh Token
            String refreshToken = tokenService.generateRefreshToken(loginRequestDTO.getUsername());

            // Return LoginResponseDTO with the generated JWT token (or relevant user details)
            return new LoginResponseDTO(username, token, refreshToken);

        } catch (AuthenticationException e) {
            // Throw an InvalidLoginException for failed authentication due to invalid login details
            throw new InvalidLoginException("Invalid Login Details");
        }
    }



}
