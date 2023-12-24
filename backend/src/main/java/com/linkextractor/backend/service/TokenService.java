package com.linkextractor.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

/**
 * Service responsible for generating JSON Web Token (JWT).
 */
@Service
public class TokenService {

    private final UserService userService;

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    public TokenService(UserService userService, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

//    public String generateAccessToken(Authentication auth) {
//        Instant now = Instant.now();
//        String scope = auth.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        return generateToken("self", now, 30, auth.getName(), "roles", scope);
//    }
//
//    public String generateRefreshToken(Authentication auth) {
//        Instant now = Instant.now();
//        return generateToken("self", now, 60, auth.getName(), "token_type", "refresh_token");
//    }
//
//    public String generateAccessTokenForRefresh(String username) {
//        UserDetails user = userService.loadUserByUsername(username);
//        Instant now = Instant.now();
//        String scope = user.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        return generateToken("self", now, 30, user.getUsername(), "roles", scope);
//    }
//
//    public boolean isValidRefreshToken(String refreshToken) {
//        try {
//            Jwt jwt = jwtDecoder.decode(refreshToken);
//            Instant expiresAt = jwt.getExpiresAt();
//
//            if (expiresAt != null && Instant.now().isBefore(expiresAt)) {
//                String tokenType = jwt.getClaim("token_type");
//                return "refresh_token".equals(tokenType);
//            }
//
//            return false;
//        } catch (JwtException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public String extractUsernameFromToken(String token) {
//        Jwt jwt = jwtDecoder.decode(token);
//        Map<String, Object> claims = jwt.getClaims();
//
//        return extractClaim(claims, "sub");
//    }
//
//    private String extractClaim(Map<String, Object> claims, String claimName) {
//        if (claims.containsKey(claimName)) {
//            return (String) claims.get(claimName);
//        }
//
//        return null;
//    }
//
//    private String generateToken(String issuer, Instant issuedAt, long expirationMinutes, String subject, String... additionalClaims) {
//        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
//                .issuer(issuer)
//                .issuedAt(issuedAt)
//                .expiresAt(issuedAt.plus(expirationMinutes, ChronoUnit.MINUTES))
//                .subject(subject);
//
//        for (int i = 0; i < additionalClaims.length; i += 2) {
//            builder.claim(additionalClaims[i], additionalClaims[i + 1]);
//        }
//
//        JwtClaimsSet claims = builder.build();
//        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }

    /**
     * Generates a JSON Web Token (JWT) based on the provided Authentication object.
     *
     * @param auth The Authentication object containing user details and authorities.
     * @return A string representing the generated JWT.
     */
    public String generateAccessToken(Authentication auth) {
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
                .expiresAt(now.plus(30, ChronoUnit.MINUTES)) // Set token expiration timestamp
                .subject(auth.getName()) // Set subject (typically user's identifier)
                .claim("roles", scope) // Set user roles in token claims
                .build();

        // Encode the JWT using the configured JwtEncoder with the constructed claims
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(String username) {
        // Generate a refresh token using JWTService
        Instant now = Instant.now();

        // Build JWT claims with issuer, issued and expiration timestamps, subject, and user roles
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // Set issuer of the token
                .issuedAt(now) // Set token issuance timestamp
                .expiresAt(now.plus(60, ChronoUnit.MINUTES)) // Set token expiration timestamp
                .subject(username) // Set subject (typically user's identifier)
                .claim("token_type", "refresh_token")
                .build();

        // Encode the JWT using the configured JwtEncoder with the constructed claims
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public boolean isValidRefreshToken(String refreshToken) {
        try {
            Jwt jwt = jwtDecoder.decode(refreshToken);
            Instant expiresAt = jwt.getExpiresAt();

            // Check if the token has expired
            if (expiresAt != null && Instant.now().isBefore(expiresAt)) {
                String tokenType = jwt.getClaim("token_type");

                // Check if the token type is 'refresh_token'
                return "refresh_token".equals(tokenType); // Token is valid and has the expected token type
            }

            return false;
        } catch (JwtException e) {
            // Log the error or handle the exception according to your requirements
            e.printStackTrace(); // Log the exception

            // Token is invalid due to a JwtException during validation
            return false;
        }

    }

    public String extractUsernameFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        Map<String, Object> claims = jwt.getClaims();

        // Extracting username from the claims
        if (claims.containsKey("sub")) {
            return (String) claims.get("sub"); // "sub" represents the subject claim which often holds the username
        }

        return null;
    }

    public String generateAccessTokenForRefresh(String username) {
        // Implement logic to generate a new access token based on the refresh token details
        // Generate a JWT access token and return it
        UserDetails user = userService.loadUserByUsername(username);

        // Get the current timestamp
        Instant now = Instant.now();

        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Build JWT claims with issuer, issued and expiration timestamps, subject, and user roles
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // Set issuer of the token
                .issuedAt(now) // Set token issuance timestamp
                .expiresAt(now.plus(30, ChronoUnit.MINUTES)) // Set token expiration timestamp
                .subject(user.getUsername()) // Set subject (typically user's identifier)
                .claim("roles", scope) // Set user roles in token claims
                .build();

        // Encode the JWT using the configured JwtEncoder with the constructed claims
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
