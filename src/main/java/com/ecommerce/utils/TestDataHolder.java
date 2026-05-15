package com.ecommerce.utils;

public class TestDataHolder {
    private static String email = "";
    private static String password = "";

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String newEmail) {
        email = newEmail;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
    }
}
