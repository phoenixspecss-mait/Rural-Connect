package com.example.ruralconnect.model;

// This class will be converted to JSON and sent TO the server
public class LoginRequest {
    String email;
    String password;

    // Constructor
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters (Retrofit/Gson needs these)
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}