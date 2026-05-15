package com.vizja.sw.lab4.encoding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Assignment: Implement the MimeEncoder class to make these tests pass.
 * You must use the java.util.Base64 MIME encoder and decoder.
 */
class MimeEncoderTest {

    private MimeEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new MimeEncoder();
    }

    /**
     * Test 1: Long String
     * This is the KEY test. It checks that the output is wrapped
     * at 76 characters with CRLF ('\r\n') line endings.
     */
    @Test
    void testLongStringWrapping() {
        // A string of 75 bytes, which encodes to 100 chars, forcing one line break.
        String original = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.";

        // The expected output, wrapped at 76 chars
        String expectedEncoded =
                "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4g\r\n"
                        + "U2VkIG5vbiByaXN1cy4=";

        String encoded = encoder.encodeLongString(original);
        assertEquals(expectedEncoded, encoded);

        // The decoder must successfully ignore the \r\n
        String decoded = encoder.decodeLongString(expectedEncoded);
        assertEquals(original, decoded);
    }

    /**
     * Test 2: Short String
     * Tests that a short string is NOT wrapped.
     */
    @Test
    void testShortString() {
        String original = "Hello MIME!";
        String expectedEncoded = "SGVsbG8gTUlNRSE="; // No line breaks

        String encoded = encoder.encodeShortString(original);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decodeShortString(expectedEncoded);
        assertEquals(original, decoded);
    }

    /**
     * Test 3: Unsafe Bytes
     * Confirms that MIME encoding uses the standard alphabet ('+' and '/')
     * and not the URL-safe one.
     */
    @Test
    void testUnsafeBytes() {
        byte[] original = new byte[]{(byte) 0xfb, (byte) 0xff};
        String expectedEncoded = "+/8=";

        String encoded = encoder.encodeUnsafeBytes(original);
        assertEquals(expectedEncoded, encoded);

        byte[] decoded = encoder.decodeUnsafeBytes(expectedEncoded);
        assertArrayEquals(original, decoded);
    }
}