package com.vizja.sw.lab4.encryptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymmetricEncryptionTest {
    @Test
    void testAES() throws Exception {
        SymmetricEncryption enc = new SymmetricEncryption("AES", "mySecretKey123");
        String cipher = enc.encrypt("hello world");
        String plain = enc.decrypt(cipher);
        assertEquals("hello world", plain);
    }

    @Test
    void testDES() throws Exception {
        SymmetricEncryption enc = new SymmetricEncryption("DES", "desKey123");
        String cipher = enc.encrypt("encryption test");
        String plain = enc.decrypt(cipher);
        assertEquals("encryption test", plain);
    }

    @Test
    void testBlowfish() throws Exception {
        SymmetricEncryption enc = new SymmetricEncryption("Blowfish", "anotherKey");
        String cipher = enc.encrypt("testing blowfish");
        String plain = enc.decrypt(cipher);
        assertEquals("testing blowfish", plain);
    }
}