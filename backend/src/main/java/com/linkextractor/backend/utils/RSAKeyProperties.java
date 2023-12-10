package com.linkextractor.backend.utils;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

/**
 * Component to hold RSA public and private keys.
 */
@Component
public class RSAKeyProperties {

    private RSAPublicKey publicKey; // Hold the RSA public key
    private RSAPrivateKey privateKey; // Hold the RSA private key


    /**
     * Constructor to generate RSA key pair and initialize public and private keys.
     */
    public RSAKeyProperties(){
        // Generate an RSA key pair using KeyGeneratorUtility class
        KeyPair pair = KeyGeneratorUtility.generateRsaKey();

        // Set the public and private keys from the generated pair
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }

    /**
     * Get the RSA public key.
     *
     * @return RSAPublicKey object representing the public key
     */
    public RSAPublicKey getPublicKey(){
        return this.publicKey;
    }

    /**
     * Set the RSA public key.
     *
     * @param publicKey RSAPublicKey object to set as the public key
     */
    public void setPublicKey(RSAPublicKey publicKey){
        this.publicKey = publicKey;
    }

    /**
     * Get the RSA private key.
     *
     * @return RSAPrivateKey object representing the private key
     */
    public RSAPrivateKey getPrivateKey(){
        return this.privateKey;
    }

    /**
     * Set the RSA private key.
     *
     * @param privateKey RSAPrivateKey object to set as the private key
     */
    public void setPrivateKey(RSAPrivateKey privateKey){
        this.privateKey = privateKey;
    }

}
