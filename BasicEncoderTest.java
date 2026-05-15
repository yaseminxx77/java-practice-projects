package com.vizja.sw.lab4.encoding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Assignment: Implement the BasicEncoder class to make these tests pass.
 * You must use the standard java.util.Base64 BASIC encoder and decoder.
 */
class BasicEncoderTest {

    private BasicEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new BasicEncoder();
    }

    /**
     * Test 1: Simple String
     * Tests encoding and decoding of a common string.
     */
    @Test
    void testSimpleString() {
        String original = "Hello Base64 World!";
        String expectedEncoded = "SGVsbG8gQmFzZTY0IFdvcmxkIQ==";

        String encoded = encoder.encodeString(original);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decodeString(expectedEncoded);
        assertEquals(original, decoded);
    }

    /**
     * Test 2: Bytes with Unsafe Chars
     * Tests byte data that encodes to '+' and '/' to confirm
     * the standard alphabet is being used.
     */
    @Test
    void testBytesWithUnsafeChars() {
        byte[] original = new byte[]{(byte) 0xfb, (byte) 0xff};
        String expectedEncoded = "+/8=";

        String encoded = encoder.encodeBytes(original);
        assertEquals(expectedEncoded, encoded);

        byte[] decoded = encoder.decodeBytes(expectedEncoded);
        assertArrayEquals(original, decoded);
    }

    /**
     * Test 3: Empty String
     * Tests the handling of an empty input.
     */
    @Test
    void testEmptyString() {
        String original = "";
        String expectedEncoded = "";

        String encoded = encoder.encodeEmpty();
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decodeEmpty(expectedEncoded);
        assertEquals(original, decoded);
    }
}