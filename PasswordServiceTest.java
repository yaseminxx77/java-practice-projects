package com.vizja.sw.lab4.hashing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This test file is your assignment.
 * DO NOT MODIFY THIS FILE.
 *
 * Your goal is to implement the classes in `src/main/java`
 * so that all tests in this file pass.
 */

class PasswordServiceTest {

    @Nested
    @DisplayName("Part 1: User hashCode() and equals() Contract")
    class UserContractTest {

        @Test
        @DisplayName("equals() should be reflexive (x.equals(x) is true)")
        void testEquals_Reflexive() {
            User u1 = new User(1L, "alice");
            assertTrue(u1.equals(u1), "A user must be equal to itself.");
        }

        @Test
        @DisplayName("equals() should be symmetric (x.equals(y) == y.equals(x))")
        void testEquals_Symmetric() {
            User u1 = new User(1L, "alice");
            User u2 = new User(1L, "alice");

            boolean u1_equals_u2 = u1.equals(u2);
            boolean u2_equals_u1 = u2.equals(u1);
            assertEquals(u1_equals_u2, u2_equals_u1, "Equality must be symmetric.");
            assertTrue(u1_equals_u2, "Two User objects with the same id and username should be equal.");
        }

        @Test
        @DisplayName("equals() should handle null (x.equals(null) is false)")
        void testEquals_Null() {
            User u1 = new User(1L, "alice");
            assertFalse(u1.equals(null), "Equality check with null must return false.");
        }

        @Test
        @DisplayName("equals() should handle different object types (x.equals(String) is false)")
        void testEquals_DifferentType() {
            User u1 = new User(1L, "alice");
            String s = "Not a User";
            assertFalse(u1.equals(s), "Equality check with a different class must return false.");
        }

        @Test
        @DisplayName("equals() should return false for different IDs")
        void testEquals_DifferentId() {
            User u1 = new User(1L, "alice");
            User u2 = new User(2L, "alice");
            assertFalse(u1.equals(u2), "Users with different IDs should not be equal.");
        }

        @Test
        @DisplayName("equals() should return false for different usernames")
        void testEquals_DifferentUsername() {
            User u1 = new User(1L, "alice");
            User u2 = new User(1L, "bob");
            assertFalse(u1.equals(u2), "Users with different usernames should not be equal.");
        }

        @Test
        @DisplayName("hashCode() Contract: equal objects MUST have equal hash codes")
        void testHashCode_Contract() {
            User u1 = new User(1L, "alice");
            User u2 = new User(1L, "alice");

            assertTrue(u1.equals(u2), "Test setup invalid: u1 and u2 should be equal.");
            assertEquals(u1.hashCode(), u2.hashCode(), "If two objects are equal, their hash codes MUST be identical.");
        }

        @Test
        @DisplayName("hashCode() Performance: unequal objects SHOULD have different hash codes")
        void testHashCode_Performance() {
            User u1 = new User(1L, "alice");
            User u2 = new User(2L, "bob");
            assertFalse(u1.equals(u2), "Test setup invalid: u1 and u2 should not be equal.");
            assertNotEquals(u1.hashCode(), u2.hashCode(), "Unequal objects SHOULD have different hash codes for good performance.");
        }

        @Test
        @DisplayName("User objects should work correctly in a HashMap")
        void testUser_InHashMap() {
            Map<User, String> userRoles = new HashMap<>();
            User alice = new User(1L, "alice");
            userRoles.put(alice, "Admin");

            User aliceLogin = new User(1L, "alice");

            String role = userRoles.get(aliceLogin);
            assertNotNull(role, "HashMap could not find the user. Check hashCode() and equals().");
            assertEquals("Admin", role, "HashMap retrieved the wrong value for the user.");
        }

        @Test
        @DisplayName("User objects should work correctly in a HashSet")
        void testUser_InHashSet() {
            Set<User> userSet = new HashSet<>();
            User alice = new User(1L, "alice");
            userSet.add(alice);

            User aliceAgain = new User(1L, "alice");

            assertTrue(userSet.contains(aliceAgain), "HashSet could not find the user. Check hashCode() and equals().");

            userSet.add(aliceAgain);
            assertEquals(1, userSet.size(), "Adding a logically equal user should not increase the set's size.");
        }
    }

    @Nested
    @DisplayName("Part 2: PasswordService Cryptographic Hashing")
    class PasswordServiceImplTest {

        private static PasswordService sha256PasswordService;

        @BeforeAll
        static void setUp() {
            sha256PasswordService = new PasswordServiceImpl("SHA-256");
        }

        @Test
        @DisplayName("hashPassword() should throw exception for null input")
        void testHash_NullInput() {
            assertThrows(IllegalArgumentException.class, () -> {
                sha256PasswordService.hashPassword(null);
            }, "hashPassword(null) should throw IllegalArgumentException.");
        }


        @Test
        @DisplayName("checkPassword() should throw exception for null input")
        void testCheck_NullInput() {
            assertThrows(IllegalArgumentException.class, () -> {
                sha256PasswordService.checkPassword(null, "some-hash");
            }, "checkPassword(null, ...) should throw IllegalArgumentException.");

            assertThrows(IllegalArgumentException.class, () -> {
                sha256PasswordService.checkPassword("some-password", null);
            }, "checkPassword(..., null) should throw IllegalArgumentException.");
        }

        @Test
        @DisplayName("hashPassword() should return a SHA-256 hash (64 hex chars)")
        void testHash_FormatAndLength() {
            String password = "mySecurePassword123";
            String hash = sha256PasswordService.hashPassword(password);

            assertNotNull(hash, "Hash should not be null.");
            assertNotEquals("", hash, "Hash should not be an empty string.");

            assertEquals(64, hash.length(), "SHA-256 hash, as hex, must be 64 characters long.");
            assertTrue(hash.matches("^[a-f0-9]{64}$"), "Hash must be a lowercase hex string.");
        }

        @Test
        @DisplayName("hashPassword() should be deterministic (same input -> same output)")
        void testHash_Deterministic() {
            String password = "my-password";
            String hash1 = sha256PasswordService.hashPassword(password);
            String hash2 = sha256PasswordService.hashPassword(password);

            assertEquals(hash1, hash2, "Hashing the same password twice must produce the same hash.");
        }

        @Test
        @DisplayName("hashPassword() should have avalanche effect (small change in input -> big change in output)")
        void testHash_AvalancheEffect() {
            String pass1 = "my-password-1";
            String pass2 = "my-password-2";

            String hash1 = sha256PasswordService.hashPassword(pass1);
            String hash2 = sha256PasswordService.hashPassword(pass2);

            assertNotEquals(hash1, hash2, "Slightly different passwords must produce different hashes.");
        }


        @Test
        @DisplayName("checkPassword() should return true for correct (SHA-256) password")
        void testCheck_CorrectPassword() {
            String password = "correct-password";
            String knownHash = "9246aa9be8de7b40d64eb664986430793b6cc13a19d2a456981e44f28303f9cf";

            assertTrue(sha256PasswordService.checkPassword(password, knownHash), "checkPassword() returned false for a correct password.");
        }

        @Test
        @DisplayName("checkPassword() should return false for incorrect (SHA-256) password")
        void testCheck_IncorrectPassword() {
            String password = "WRONG-password";
            String knownHash = "b0fac6558e82a932822a86b97155018aa1290f672322af6018ad838f5f26a117";

            assertFalse(sha256PasswordService.checkPassword(password, knownHash), "checkPassword() returned true for an incorrect password.");
        }


        @Test
        @DisplayName("Service should work with other algorithms (SHA-512)")
        void testHash_WithDifferentAlgorithm_SHA512() {

            PasswordService sha512Service = new PasswordServiceImpl("SHA-512");
            String password = "my-sha512-password";

            String knownSha512Hash = sha512Service.hashPassword(password);
            String generatedHash = sha512Service.hashPassword(password);
            assertEquals(128, generatedHash.length(), "SHA-512 hash, as hex, must be 128 characters long.");

            assertEquals(knownSha512Hash, generatedHash, "Generated SHA-512 hash does not match known value.");


            assertTrue(sha512Service.checkPassword(password, knownSha512Hash), "checkPassword() failed for SHA-512 with correct password.");
            assertFalse(sha512Service.checkPassword("wrong-pass", knownSha512Hash), "checkPassword() passed for SHA-512 with incorrect password.");
        }

        @Test
        @DisplayName("Constructor should throw exception for invalid algorithm")
        void testConstructor_InvalidAlgorithm() {
            assertThrows(IllegalArgumentException.class, () -> {
                new PasswordServiceImpl("NOT-A-REAL-ALGORITHM");
            }, "Constructor should throw exception for an algorithm that doesn't exist.");

            assertThrows(IllegalArgumentException.class, () -> {
                new PasswordServiceImpl(null);
            }, "Constructor should throw exception for null algorithm.");
        }
    }
}
