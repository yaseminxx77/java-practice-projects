package com.vizja.sw.lab4.encoding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Assignment: Implement the UrlEncoder class to make these tests pass.
 * You must use the java.util.Base64 URL AND FILENAME SAFE encoder and decoder.
 */
class UrlEncoderTest {

    private UrlEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new UrlEncoder();
    }

    /**
     * Test 1: URL String
     * Tests encoding/decoding of a string containing URL query params.
     */
    @Test
    void testUrlString() {
        String original = "users?action=query&format=json";
        String expectedEncoded = "dXNlcnM_YWN0aW9uPXF1ZXJ5JmZvcm1hdD1qc29u";

        String encoded = encoder.encodeUrl(original);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decodeUrl(expectedEncoded);
        assertEquals(original, decoded);
    }

    /**
     * Test 2: Unsafe Bytes
     * This is the KEY test. It uses bytes that would be '+' and '/'
     * in Basic encoding, but must be '-' and '_' here.
     */
    @Test
    void testEncodeUnsafeBytes() {
        // These bytes encode to "+/8=" in Basic, but "-_8=" in URL-safe
        byte[] original = new byte[]{(byte) 0xfb, (byte) 0xff};
        String expectedEncoded = "-_8=";

        String encoded = encoder.encodeUnsafeBytes(original);
        assertEquals(expectedEncoded, encoded);

        byte[] decoded = encoder.decodeUnsafeBytes(expectedEncoded);
        assertArrayEquals(original, decoded);
    }

    /**
     * Test 3: Simple String
     * Shows that for simple strings without the special chars,
     * the output is identical to Basic encoding.
     */
    @Test
    void testSimpleString() {
        String original = "Just a regular string.";
        String expectedEncoded = "SnVzdCBhIHJlZ3VsYXIgc3RyaW5nLg==";

        String encoded = encoder.encodeSimpleString(original);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decodeSimpleString(expectedEncoded);
        assertEquals(original, decoded);
    }
}