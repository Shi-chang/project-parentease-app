package edu.northeastern.myapplication.entity;

import java.util.Map;

/**
 * The nanny information class.
 */
public class NannyInfo {
    private String userId;
    private int yoe;
    private String gender;
    private Review[] reviews;
    private float hourlyRate;
    private Map<String, Boolean> availability;
    private float ratings;

    public NannyInfo() {
    }

    public NannyInfo(String userId, int yoe, String gender, Review[] reviews, float hourlyRate, Map<String, Boolean> availability, float ratings) {
        this.userId = userId;
        this.yoe = yoe;
        this.gender = gender;
        this.reviews = reviews;
        this.hourlyRate = hourlyRate;
        this.availability = availability;
        this.ratings = ratings;
    }

    public String getUserId() {
        return userId;
    }

    public int getYoe() {
        return yoe;
    }

    public String getGender() {
        return gender;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public Map<String, Boolean> getAvailability() {
        return availability;
    }

    public float getRatings() {
        return ratings;
    }
}
