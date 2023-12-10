package com.linkextractor.backend.config;

<<<<<<< HEAD
import com.linkextractor.backend.models.enums.Roles;
=======
import org.springframework.cache.annotation.EnableCaching;
>>>>>>> authAndSecurity
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.linkextractor.backend.utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.Bucket;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

<<<<<<< HEAD
=======
import java.time.Duration;
import java.util.Arrays;
>>>>>>> authAndSecurity
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Configuration class responsible for managing security configurations,
 * including authentication, authorization, and CORS policies.
 */
@Configuration
@EnableCaching
public class SecurityConfiguration {
<<<<<<< HEAD

    // Constants for endpoint URLs
    private static final String AUTH_ENDPOINT = "/auth/**";
    private static final String ADMIN_ENDPOINT = "/admin/**";
    private static final String USER_ENDPOINT = "/user/**";

=======
>>>>>>> authAndSecurity
    private final RSAKeyProperties keys;

    // Constructor injection for RSAKeyProperties
    public SecurityConfiguration(RSAKeyProperties keys) {
        this.keys = keys;
    }


    /**
     * Defines the password encoder bean to encode passwords using BCrypt.
     *
     * @return BCryptPasswordEncoder instance for password encoding
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Configures the authentication manager bean to handle authentication requests.
     *
     * @param detailsService UserDetailsService for user-related details retrieval
     * @return AuthenticationManager instance for authentication
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    /**
     * Configures the security filter chain to define security rules for different endpoints.
     * Specifies endpoint permissions, JWT token validation, and session management.
     *
     * @param http HttpSecurity instance for configuring security
     * @return SecurityFilterChain instance defining security rules
     * @throws Exception if configuration encounters an error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Configure CORS globally by default, using a bean by the name of corsConfigurationSource
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection because a custom token is used
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_ENDPOINT).permitAll(); // Permit access to /auth/** endpoints
                    auth.requestMatchers(ADMIN_ENDPOINT).hasRole(Roles.ADMIN.name()); // Require ADMIN role for /admin/**
                    auth.requestMatchers(USER_ENDPOINT).hasAnyRole(Roles.ADMIN.name(), Roles.USER.name()); // Require ADMIN or USER roles for /user/**
                    auth.anyRequest().authenticated(); // Require authentication for any other request
                })
                // Configures JWT token validation for OAuth2 resource server
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                // Configures stateless sessions for improved security (This means it re-authenticates the user on every request.)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build(); // Builds the SecurityFilterChain
    }


    /**
     * Configures CORS globally allowing requests from all origins, methods, and headers.
     * Note: This approach might not be considered best for CORS configuration (Need to change this).
     *
     * @return CorsConfigurationSource instance for CORS configuration
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // Create CORS configuration instance
        configuration.setAllowedOrigins(List.of("*")); // Allow requests from all origins
        configuration.setAllowedMethods(List.of("*")); // Allow all HTTP methods
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        // Creating a UrlBasedCorsConfigurationSource instance to register the CorsConfiguration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Register CORS configuration for all endpoints
        return source;
    }

    /**
     * Defines the JWT Decoder bean to decode JWT tokens.
     * It uses a provided public key for decoding JWT tokens.
     *
     * @return JwtDecoder instance for decoding JWT tokens
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // Create a JwtDecoder instance using NimbusJwtDecoder with the provided public key
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }


    /**
     * Defines the JWT Encoder bean to encode JWT tokens.
     * It utilizes provided public and private keys for encoding JWT tokens.
     *
     * @return JwtEncoder instance for encoding JWT tokens
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        // Create a JWK (JSON Web Key) using provided public and private keys
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        // Create a JWKSource with the created JWK
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        // Create a JwtEncoder using NimbusJwtEncoder with the JWKSource
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Method to configure JWT Authentication Converter to handle roles in JWT tokens.
     *
     * @return JwtAuthenticationConverter configured for handling roles in JWT tokens
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Specifies the key in JWT payload containing role information
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        // Prefixes roles with "ROLE_" as required by Spring Security
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }

}
