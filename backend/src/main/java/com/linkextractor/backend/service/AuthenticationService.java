package com.linkextractor.backend.service;

import java.util.HashSet;
import java.util.Set;

import com.linkextractor.backend.exceptions.InvalidLoginException;
import com.linkextractor.backend.exceptions.UserRegistrationException;
import com.linkextractor.backend.dto.LoginDTO;
import com.linkextractor.backend.dto.RegistrationDTO;
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
    private final JWTService jwtService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user based on the provided registration information.
     *
     * @param registrationDTO The RegistrationDTO containing user registration details.
     * @return The newly registered User object.
     * @throws UserRegistrationException if username or email already exists.
     */
    public User registerUser(RegistrationDTO registrationDTO) {
        // Check if username or email already exist in the database
        if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
            throw new UserRegistrationException("Username already exists");
        }

        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new UserRegistrationException("Email already exists");
        }

        // Encode the password before saving it to the database
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        // Create and save the new User object
        return userRepository.save(new User(0, registrationDTO.getUsername(), encodedPassword, registrationDTO.getEmail(), registrationDTO.getFirstname(), registrationDTO.getLastname(), authorities));
    }

    /**
     * Authenticates a user based on the provided login information.
     *
     * @param loginDTO The LoginDTO containing user login details.
     * @return The LoginResponseDTO with user details and authentication token upon successful authentication.
     * @throws InvalidLoginException if authentication fails with invalid login details.
     */
    public LoginResponseDTO loginUser(LoginDTO loginDTO) {
        try {
            // Authenticate user credentials using AuthenticationManager
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            // Generate a JWT token for the authenticated user
            String token = jwtService.generateJwt(auth);

            // Return LoginResponseDTO with the generated JWT token (or relevant user details)
            return new LoginResponseDTO(token);

        } catch (AuthenticationException e) {
            // Throw an InvalidLoginException for failed authentication due to invalid login details
            throw new InvalidLoginException("Invalid Login Details");
        }
    }

}
