package com.example.ruralconnect.model;

// This class will be converted to JSON and sent TO the server
public class RegisterRequest {
    String name;
    String email;
    String password;
    String phoneNumber;

    // Constructor
    public RegisterRequest(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // --- Generate Getters and Setters for all 4 fields ---
}