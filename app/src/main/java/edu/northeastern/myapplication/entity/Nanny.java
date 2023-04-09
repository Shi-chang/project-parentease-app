package edu.northeastern.myapplication.entity;

import java.util.List;

/**
 * The nanny class.
 */
public class Nanny {
    private String userId;
    private int yoe;
    private String gender;
    private int hourlyRate;
    private List<TimeSlot> availability;
    private float ratings;
    private String introduction;

    public Nanny() {
    }

    public Nanny(String userId, int yoe, String gender, int hourlyRate, List<TimeSlot> availability, float ratings, String introduction) {
        this.userId = userId;
        this.yoe = yoe;
        this.gender = gender;
        this.hourlyRate = hourlyRate;
        this.availability = availability;
        this.ratings = ratings;
        this.introduction = introduction;
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

    public float getHourlyRate() {
        return hourlyRate;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public float getRatings() {
        return ratings;
    }

    public String getIntroduction() {
        return introduction;
    }
}
