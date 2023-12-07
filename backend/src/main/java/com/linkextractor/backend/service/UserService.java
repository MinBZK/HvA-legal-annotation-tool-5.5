package com.linkextractor.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.linkextractor.backend.respositories.UserRepository;

/**
 * Service class that implements the UserDetailsService interface to provide user-related functionalities.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;


    /**
     * Retrieves a UserDetails object based on the username.
     *
     * @param username The username for which UserDetails are requested.
     * @return UserDetails object associated with the provided username.
     * @throws UsernameNotFoundException if the user is not found in the repository.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Check!");
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}

