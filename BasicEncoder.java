package com.vizja.sw.lab4.encoding;

import java.util.Base64;

public class BasicEncoder {

    public String encodeString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decodeString(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

    public String encodeBytes(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    public byte[] decodeBytes(String input) {
        return Base64.getDecoder().decode(input);
    }

    public String encodeEmpty() {
        return "";
    }

    public String decodeEmpty(String input) {
        return "";
    }
}
