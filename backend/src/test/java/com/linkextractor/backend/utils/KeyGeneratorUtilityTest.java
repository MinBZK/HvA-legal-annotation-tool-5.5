package com.linkextractor.backend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KeyGeneratorUtilityTest {

    /**
     * Tests the generation of a valid RSA key pair.
     */
    @Test
    public void generateRsaKey_ReturnsValidKeyPair() {
        // Arrange & Act
        KeyPair keyPair = KeyGeneratorUtility.generateRsaKey();

        // Assert
        assertNotNull(keyPair);
        assertNotNull(keyPair.getPublic());
        assertNotNull(keyPair.getPrivate());

        // Check Key Algorithm
        assertEquals("RSA", keyPair.getPublic().getAlgorithm());
        assertEquals("RSA", keyPair.getPrivate().getAlgorithm());
    }

    /**
     * Ensures that calling generateRsaKey multiple times produces different key pairs.
     */
    @Test
    public void generateRsaKey_MultipleCallsProduceDifferentKeyPairs() {
        // Arrange & Act
        KeyPair keyPair1 = KeyGeneratorUtility.generateRsaKey();
        KeyPair keyPair2 = KeyGeneratorUtility.generateRsaKey();

        // Assert
        assertNotEquals(keyPair1.getPublic(), keyPair2.getPublic());
        assertNotEquals(keyPair1.getPrivate(), keyPair2.getPrivate());
    }

    /**
     * Checks that the format of the public and private keys is correct.
     */
    @Test
    public void checkKeyFormat_PublicAndPrivateKeyHaveCorrectFormat() {
        // Arrange
        KeyPair keyPair = KeyGeneratorUtility.generateRsaKey();

        // Act & Assert
        checkKeyFormat(keyPair.getPublic(), "Public");
        checkKeyFormat(keyPair.getPrivate(), "Private");
    }

    // Helper method to check the format of the key
    private void checkKeyFormat(Object key, String keyType) {
        // Assert
        assertNotNull(key, keyType + " key should not be null");
        assertTrue(key instanceof java.security.Key, keyType + " key should implement java.security.Key");

        if (keyType.equals("Public")) {
            assertTrue(key instanceof RSAPublicKey, keyType + " key should be of type RSAPublicKey");
        } else if (keyType.equals("Private")) {
            assertTrue(key instanceof RSAPrivateKey, keyType + " key should be of type RSAPrivateKey");
        }
    }
}
