package com.vizja.sw.lab4.encryptions;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class SymmetricEncryption {

    private final String algorithm;
    private final SecretKey secretKey;
    public SymmetricEncryption(String algorithm, String key) throws Exception {
        if (algorithm == null || key == null) {
            throw new IllegalArgumentException("Algorithm and key cannot be null");
        }

        this.algorithm = algorithm;

        byte[] keyBytes;
        if (algorithm.equalsIgnoreCase("Blowfish")) {
            // Blowfish minimum 8 bytes key
            keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 16);
        } else if (algorithm.equalsIgnoreCase("AES")) {
            // AES needs 16, 24, or 32 bytes
            keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 16);
        } else if (algorithm.equalsIgnoreCase("DES")) {
            // DES key = 8 bytes
            keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 8);
        } else {
            throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }

        this.secretKey = new SecretKeySpec(keyBytes, algorithm);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
