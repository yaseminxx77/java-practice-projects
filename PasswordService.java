package com.vizja.sw.lab4.hashing;

public interface PasswordService {

    String hashPassword(String password);
    boolean checkPassword(String password, String hashedPassword);
}
