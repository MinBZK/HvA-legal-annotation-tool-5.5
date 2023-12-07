package com.linkextractor.backend.service;

import java.util.HashSet;
import java.util.Set;

import com.linkextractor.backend.dto.LoginDTO;
import com.linkextractor.backend.dto.RegistrationDTO;
import com.linkextractor.backend.dto.LoginResponseDTO;
import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.respositories.RoleRepository;
import com.linkextractor.backend.respositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User registerUser(RegistrationDTO registrationDTO) {

        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new User(0, registrationDTO.getUsername(), encodedPassword, registrationDTO.getEmail(), registrationDTO.getFirstname(), registrationDTO.getLastname(), authorities));
    }

    public LoginResponseDTO loginUser(LoginDTO loginDTO) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(loginDTO.getUsername()).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }

}
