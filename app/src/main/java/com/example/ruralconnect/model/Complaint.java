package com.example.ruralconnect.model;

import java.io.Serializable;

public class Complaint implements Serializable {

    // Fields from backend/API
    private Long id;
    private User user;
    private String status;
    private String createdAt;
    private String imageUrl; // Keep for future

    // --- Fields from the form ---
    private String name;
    private String contactNumber;
    private String complaintType;
    private String location;
    private String complaintText;
    // --- Removed title, description if they exist ---

    // Default constructor for Gson/Retrofit
    public Complaint() {
    }

    // --- Generate Getters and Setters for ALL fields ---
    // In Android Studio: Right-click -> Generate -> Getters and Setters -> Select All

    // Example Getters/Setters (Generate the rest)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getComplaintType() { return complaintType; }
    public void setComplaintType(String complaintType) { this.complaintType = complaintType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getComplaintText() { return complaintText; }
    public void setComplaintText(String complaintText) { this.complaintText = complaintText; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}