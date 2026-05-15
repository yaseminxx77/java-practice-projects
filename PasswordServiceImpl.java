package com.vizja.sw.lab4.hashing;

import java.security.MessageDigest;
import java.util.Locale;

public class PasswordServiceImpl implements PasswordService {

    private final String algorithm;

    public PasswordServiceImpl(String algorithm) {
        try {
            MessageDigest.getInstance(algorithm);
            this.algorithm = algorithm;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
        }
    }

    @Override
    public String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hashed) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString().toLowerCase(Locale.ROOT);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    @Override
    public boolean checkPassword(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        String newHash = hashPassword(password);
        return newHash.equalsIgnoreCase(hashedPassword);
    }
}
