package com.example.ruralconnect.model;

public class Feedback {

    // Fields you get back from the server
    private Long id;
    private User user; // The user who submitted it
    private String createdAt;

    // Fields you send to the server
    private int rating; // The 5-star rating (1-5)
    private String experienceText; // "Tell us about your experience..."

    // Default constructor for Gson
    public Feedback() {
    }

    // A helpful constructor for creating a new feedback
    public Feedback(int rating, String experienceText) {
        this.rating = rating;
        this.experienceText = experienceText;
    }

    // --- Generate Getters and Setters for all 5 fields ---
    // In Android Studio: Alt + Insert -> Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getExperienceText() {
        return experienceText;
    }

    public void setExperienceText(String experienceText) {
        this.experienceText = experienceText;
    }
}