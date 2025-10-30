package com.example.ruralconnect.model;

// Note: We use String for the date to make it easy for Gson.
// No special adapters are needed.
public class Scheme {

    private Long id;
    private String title;
    private String description;
    private String eligibility;
    private String benefits;
    private String applyLink;
    private String lastDateToApply; // Use String to keep it simple

    // A default empty constructor is good for Gson
    public Scheme() {
    }

    // --- Generate Getters and Setters for all 7 fields ---
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

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public String getLastDateToApply() {
        return lastDateToApply;
    }

    public void setLastDateToApply(String lastDateToApply) {
        this.lastDateToApply = lastDateToApply;
    }
}