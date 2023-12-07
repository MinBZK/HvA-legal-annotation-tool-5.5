package com.linkextractor.backend.controllers;

import com.linkextractor.backend.dto.LoginDTO;
import com.linkextractor.backend.dto.LoginResponseDTO;
import com.linkextractor.backend.dto.RegistrationDTO;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegistrationDTO registrationDTO){
        return ResponseEntity.ok(authenticationService.registerUser(registrationDTO));
    }

    // TODO LoginDTO, better messages and change the return
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.loginUser(loginDTO));
    }
}
