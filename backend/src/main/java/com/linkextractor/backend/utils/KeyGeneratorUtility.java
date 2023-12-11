package com.linkextractor.backend.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Class for generating RSA key pairs.
 */
public class KeyGeneratorUtility {

    /**
     * Generates an RSA key pair.
     *
     * @return KeyPair containing the generated public and private keys
     * @throws IllegalStateException if there's an issue generating the keys
     */
    public static KeyPair generateRsaKey(){

        KeyPair keyPair;

        try{
            // Initializing KeyPairGenerator with RSA algorithm and key size of 2048 bits
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            // Generating the key pair
            keyPair = keyPairGenerator.generateKeyPair();
        } catch(Exception e){
            // Throw an IllegalStateException if an exception occurs during key generation
            throw new IllegalStateException();
        }

        return keyPair;
    }
}
