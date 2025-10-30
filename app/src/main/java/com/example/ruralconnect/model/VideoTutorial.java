package com.example.ruralconnect.model;

import java.io.Serializable;

public class VideoTutorial implements Serializable{

    private Long id;
    private String title;
    private String description;
    private String videoUrl; // This will be the YouTube URL

    // Default constructor for Gson
    public VideoTutorial() {
    }

    // --- Generate Getters and Setters for all 4 fields ---
    // In Android Studio: Alt + Insert -> Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}