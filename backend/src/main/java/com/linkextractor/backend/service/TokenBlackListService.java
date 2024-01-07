package com.linkextractor.backend.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service responsible for managing token blacklisting operations.
 * Tokens can be added to the blacklist, checked for blacklisting, and removed if needed.
 * Additionally, it includes a scheduled task to remove expired tokens from the blacklist.
 */
@Service
public class TokenBlackListService {

    // Map to store blacklisted tokens along with their creation timestamps
    private final Map<String, Instant> tokenBlacklist = new ConcurrentHashMap<>();

    // Duration after which a token is considered expired and should be removed from the blacklist
    private final Duration expirationDuration = Duration.ofMinutes(20);

    /**
     * Add a token to the blacklist along with the current timestamp.
     *
     * @param token The token to be added to the blacklist.
     */
    public void addToBlacklist(String token) {
        tokenBlacklist.put(token, Instant.now());
    }

    /**
     * Check if a token is present in the blacklist.
     *
     * @param token The token to be checked.
     * @return True if the token is blacklisted, otherwise false.
     */
    public boolean isBlacklisted(String token) {
        return tokenBlacklist.containsKey(token);
    }

    /**
     * Remove a token from the blacklist.
     *
     * @param token The token to be removed from the blacklist.
     */
    public void removeFromBlacklist(String token) {
        tokenBlacklist.remove(token);
    }

    /**
     * Method scheduled to run at a fixed delay to remove expired tokens from the blacklist.
     * Runs every specified time interval to check and remove tokens that have expired.
     */
    @Scheduled(fixedDelay = 600000) // Run every 10 minutes (600,000 milliseconds)
    public void removeExpiredTokens() {
        Instant now = Instant.now();

        // Iterate through the tokens to check their expiration
        tokenBlacklist.entrySet().removeIf(entry ->
                entry.getValue().plus(expirationDuration).isBefore(now)
        );
    }
}
