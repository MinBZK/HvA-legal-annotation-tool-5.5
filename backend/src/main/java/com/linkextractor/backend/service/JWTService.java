package com.linkextractor.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * Service responsible for generating JSON Web Token (JWT).
 */
@Service
public class JWTService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JWTService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Generates a JSON Web Token (JWT) based on the provided Authentication object.
     *
     * @param auth The Authentication object containing user details and authorities.
     * @return A string representing the generated JWT.
     */
    public String generateJwt(Authentication auth) {
        // Get the current timestamp
        Instant now = Instant.now();

        // Extract user roles from Authentication object and concatenate into a space-separated string
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Build JWT claims with issuer, issued and expiration timestamps, subject, and user roles
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // Set issuer of the token
                .issuedAt(now) // Set token issuance timestamp
                .expiresAt(now.plus(15, ChronoUnit.MINUTES)) // Set token expiration timestamp
                .subject(auth.getName()) // Set subject (typically user's identifier)
                .claim("roles", scope) // Set user roles in token claims
                .build();

        // Encode the JWT using the configured JwtEncoder with the constructed claims
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
