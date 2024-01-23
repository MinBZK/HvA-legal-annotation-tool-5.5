package com.linkextractor.backend.service;

import com.linkextractor.backend.service.TokenBlackListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenBlackListServicesTest {
    @Autowired
    private TokenBlackListService tokenBlackListService;

    private TokenBlackListService shortExpiryService;
    private TokenBlackListService longExpiryService;

    /**
     * Set up two instances of TokenBlackListService with different expiration durations.
     */
    @BeforeEach
    public void setUp() {
        shortExpiryService = createTokenBlackListServiceWithExpiration(Duration.ofSeconds(0));
        longExpiryService = createTokenBlackListServiceWithExpiration(Duration.ofMinutes(5));
    }

    /**
     * Helper method to create a new TokenBlackListService with the specified expiration duration.
     *
     * @param expirationDuration The expiration duration for the TokenBlackListService.
     * @return A new TokenBlackListService instance.
     */
    private TokenBlackListService createTokenBlackListServiceWithExpiration(Duration expirationDuration) {
        TokenBlackListService service = new TokenBlackListService();
        setExpirationDurationHelper(service, expirationDuration);
        return service;
    }

    /**
     * Tests the addition of a token to the blacklist and verifies that the token is blacklisted.
     */
    @Test
    public void addToBlacklist_ShouldAddTokenToBlacklist() {
        // Arrange
        String token = "testToken";

        // Act: Add the token to the blacklist
        tokenBlackListService.addToBlacklist(token);

        // Assert: Verify that the token is blacklisted
        assertTrue(tokenBlackListService.isBlacklisted(token));
    }

    /**
     * Tests if a token is correctly identified as blacklisted.
     */
    @Test
    public void isBlacklisted_ShouldReturnTrueForBlacklistedToken() {
        // Arrange
        String token = "testToken";

        // Arrange: Add the token to the blacklist
        tokenBlackListService.addToBlacklist(token);

        // Act: Check if the token is blacklisted
        boolean isBlacklisted = tokenBlackListService.isBlacklisted(token);

        // Assert: Verify that the token is blacklisted
        assertTrue(isBlacklisted);
    }

    /**
     * Tests the removal of expired tokens based on the expiration duration of the TokenBlackListService.
     *
     * @param token The token to test for expiration.
     * @throws InterruptedException If the thread is interrupted.
     */
    @ParameterizedTest
    @ValueSource(strings = {"shortExpiryToken", "longExpiryToken"})
    public void tokenExpirationDuration_ShouldRemoveExpiredTokens(String token) throws InterruptedException {
        // Act: Add tokens to both services
        shortExpiryService.addToBlacklist(token);
        longExpiryService.addToBlacklist(token);

        // Act: Simulate short expiration duration (remove expired tokens)
        shortExpiryService.removeExpiredTokens();

        // Assert: Verify that the short-expiry service removed the token, but long-expiry did not
        assertFalse(shortExpiryService.isBlacklisted(token));
        assertTrue(longExpiryService.isBlacklisted(token));
    }

    /**
     * Tests the removal of a token from the blacklist and verifies that the token is no longer blacklisted.
     */
    @Test
    public void removeFromBlacklist_ShouldRemoveTokenFromBlacklist() {
        // Arrange
        String token = "testToken";
        // Arrange: Add the token to the blacklist
        tokenBlackListService.addToBlacklist(token);

        // Act: Remove the token from the blacklist
        tokenBlackListService.removeFromBlacklist(token);

        // Assert: Verify that the token is not blacklisted after removal
        assertFalse(tokenBlackListService.isBlacklisted(token));
    }

    /**
     * Tests the removal of a non-existing token from the blacklist and ensures no exception is thrown.
     */
    @Test
    public void removeFromBlacklist_ShouldNotThrowExceptionForNonExistingToken() {
        // Arrange
        String nonExistingToken = "nonExistingToken";

        // Assert
        tokenBlackListService.removeFromBlacklist(nonExistingToken);


        assertFalse(tokenBlackListService.isBlacklisted(nonExistingToken));
    }

    /**
     * Helper method to set the expiration duration of a TokenBlackListService using reflection.
     *
     * @param service   The TokenBlackListService to set the expiration duration for.
     * @param duration  The expiration duration to set.
     */
    private void setExpirationDurationHelper(TokenBlackListService service, Duration duration) {
        try {
            Field field = TokenBlackListService.class.getDeclaredField("expirationDuration");
            field.setAccessible(true);
            field.set(service, duration);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }
}
