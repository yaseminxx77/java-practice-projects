package com.vizja.swp.lab2.app.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    // ---------------- Singleton Instance ----------------
    private static final UserRepository instance = new UserRepository();
    public static UserRepository getInstance() {
        return instance;
    }

    // -----------------------------------------------------
    private final List<User> users = new ArrayList<>();
    private int idCounter = 1;

    private UserRepository() {}  // private constructor


    // ---------------- SAVE USER ----------------
    public User save(String username, String password, String email) {

        User u = new User();
        u.setId(idCounter++);
        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(email);
        u.setActivated(false);

        // benzersiz aktivasyon token
        u.setActivationToken(UUID.randomUUID().toString());

        users.add(u);
        return u;
    }


    // ---------------- FIND BY USERNAME ----------------
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


    // ---------------- FIND BY TOKEN ----------------
    public User findByToken(String token) {
        return users.stream()
                .filter(u -> token.equals(u.getActivationToken()))
                .findFirst()
                .orElse(null);
    }
}
