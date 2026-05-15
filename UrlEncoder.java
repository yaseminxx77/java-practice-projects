package com.vizja.sw.lab4.encoding;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UrlEncoder {

    public String encodeUrl(String input) {
        if (input == null) throw new IllegalArgumentException("Input cannot be null");
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    public String decodeUrl(String encoded) {
        if (encoded == null) throw new IllegalArgumentException("Encoded value cannot be null");
        byte[] decoded = Base64.getUrlDecoder().decode(encoded);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    // 2️⃣ Byte tabanlı Base64 encode/decode (URL-safe)
    public String encodeUnsafeBytes(byte[] input) {
        if (input == null) throw new IllegalArgumentException("Input bytes cannot be null");
        return Base64.getUrlEncoder().encodeToString(input);
    }

    public byte[] decodeUnsafeBytes(String encoded) {
        if (encoded == null) throw new IllegalArgumentException("Encoded string cannot be null");
        return Base64.getUrlDecoder().decode(encoded);
    }

    public String encodeSimpleString(String input) {
        if (input == null) throw new IllegalArgumentException("Input cannot be null");
        return Base64.getUrlEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    public String decodeSimpleString(String encoded) {
        if (encoded == null) throw new IllegalArgumentException("Encoded string cannot be null");
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encoded);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
