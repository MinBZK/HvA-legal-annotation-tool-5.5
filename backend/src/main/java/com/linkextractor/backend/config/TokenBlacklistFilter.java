package com.linkextractor.backend.config;

import com.linkextractor.backend.service.TokenBlackListService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to check if incoming requests carry a blacklisted token.
 * This filter intercepts incoming requests and checks if the token is blacklisted,
 * returning an unauthorized error if the token is found in the blacklist.
 */
@Component
public class TokenBlacklistFilter extends OncePerRequestFilter {
    private final TokenBlackListService tokenBlackListService;

    public TokenBlacklistFilter(TokenBlackListService tokenBlackListService) {
        this.tokenBlackListService = tokenBlackListService;
    }

    /**
     * Method to perform the filtering of incoming requests.
     *
     * @param request     HTTP request received by the server.
     * @param response    HTTP response that will be sent back.
     * @param filterChain Chain of filters to be invoked for this request.
     * @throws ServletException In case of servlet-related issues.
     * @throws IOException      In case of input/output errors.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request); // Extract the token from the request

        if (token != null) {
            // Check if the token is blacklisted
            boolean isBlacklisted = tokenBlackListService.isBlacklisted(token);

            if (isBlacklisted) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                return; // Stop processing the request further as the token is blacklisted
            }
        }
        // Continue the request processing by passing it to the next filter in the chain
        filterChain.doFilter(request, response);
    }

    /**
     * Method to extract the token from the Authorization header.
     *
     * @param request HTTP request from which the token is extracted.
     * @return Extracted token or null if not found.
     */
    private String extractToken(HttpServletRequest request) {
        // Retrieve the Authorization header from the HTTP request
        String header = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (header != null && header.startsWith("Bearer ")) {
            // Extract the token by removing the "Bearer " prefix
            return header.substring(7);
        }

        // Return null if the Authorization header is absent or doesn't start with "Bearer "
        return null;
    }
}
