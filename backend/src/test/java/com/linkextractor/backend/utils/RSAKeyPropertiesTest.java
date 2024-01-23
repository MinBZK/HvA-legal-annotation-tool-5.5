package com.linkextractor.backend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RSAKeyPropertiesTest {

    /**
     * Tests the initialization of RSAKeyProperties and checks if public and private keys are set.
     */
    @Test
    public void rsaKeyProperties_InitializedCorrectly() {
        // Arrange
        RSAKeyProperties rsaKeyProperties = new RSAKeyProperties();

        // Act
        RSAPublicKey publicKey = rsaKeyProperties.getPublicKey();
        RSAPrivateKey privateKey = rsaKeyProperties.getPrivateKey();

        // Assert
        assertNotNull(publicKey, "Public key should not be null");
        assertNotNull(privateKey, "Private key should not be null");
    }

    /**
     * Tests if setPublicKey and setPrivateKey methods correctly update the public and private keys.
     */
    @Test
    public void rsaKeyProperties_SetKeys_UpdateKeys() {
        // Arrange
        RSAKeyProperties rsaKeyProperties = new RSAKeyProperties();
        RSAPublicKey newPublicKey = (RSAPublicKey) KeyGeneratorUtility.generateRsaKey().getPublic();
        RSAPrivateKey newPrivateKey = (RSAPrivateKey) KeyGeneratorUtility.generateRsaKey().getPrivate();

        // Act
        rsaKeyProperties.setPublicKey(newPublicKey);
        rsaKeyProperties.setPrivateKey(newPrivateKey);

        // Assert
        assertEquals(newPublicKey, rsaKeyProperties.getPublicKey(), "Public key should be updated");
        assertEquals(newPrivateKey, rsaKeyProperties.getPrivateKey(), "Private key should be updated");
    }
}
