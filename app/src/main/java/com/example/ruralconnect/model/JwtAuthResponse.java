package com.example.ruralconnect.model;

// This class will be created FROM the JSON response from the server
public class JwtAuthResponse {
    private String token;
    private String tokenType;
    private String role;

    // --- Generate Getters and Setters for all 3 fields ---
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}