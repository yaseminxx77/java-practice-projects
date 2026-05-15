package com.vizja.swp.lab2.app.jwt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class JwtUtil {

    private static final String SECRET = "my-secret-key"; // HMAC için secret

    // ---------------------- CREATE TOKEN ----------------------
    public static String createToken(String username) {
        long expiry = System.currentTimeMillis() + 60 * 60 * 1000; // 1 saat geçerli

        String header = base64Url("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String payload = base64Url("{\"username\":\"" + username + "\",\"exp\":" + expiry + "}");

        String signature = hmacSha256(header + "." + payload);

        return header + "." + payload + "." + signature;
    }

    public static boolean isValid(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];
            String expectedSig = hmacSha256(header + "." + payload);
            if (!expectedSig.equals(signature)) return false;

            String payloadJson = new String(Base64.getUrlDecoder().decode(payload));
            long exp = Long.parseLong(payloadJson.replaceAll(".*\"exp\":(\\d+).*", "$1"));

            return exp > System.currentTimeMillis();

        } catch (Exception e) {
            return false;
        }
    }

    private static String base64Url(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes());
    }

    private static String hmacSha256(String data) {
        try {
            Mac sha256 = Mac.getInstance("HmacSHA256");
            sha256.init(new SecretKeySpec(SECRET.getBytes(), "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(sha256.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
