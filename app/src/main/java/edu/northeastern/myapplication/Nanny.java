package edu.northeastern.myapplication;

import androidx.recyclerview.widget.RecyclerView;

public class Nanny {
    //Nanny: Name, Gender, birthday, review score, Year of experience, location
    private String name;
    private String gender;
    private String birthday;
    private double reviewScore;
    private double yoe;
    private String location;

    public Nanny(String name, String gender, String birthday, double reviewScore, double yoe, String location) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.reviewScore = reviewScore;
        this.yoe = yoe;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(double reviewScore) {
        this.reviewScore = reviewScore;
    }

    public double getYoe() {
        return yoe;
    }

    public void setYoe(double yoe) {
        this.yoe = yoe;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
