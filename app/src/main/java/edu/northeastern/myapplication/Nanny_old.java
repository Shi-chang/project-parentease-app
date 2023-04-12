package edu.northeastern.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Nanny_old implements Parcelable {
    //Nanny: Name, Gender, birthday, review score, Year of experience, location
    private String name;
    private String gender;
    private String birthday;
    private double reviewScore;
    private double yoe;
    private String location;

    public Nanny_old(String name, String gender, String birthday, double reviewScore, double yoe, String location) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.reviewScore = reviewScore;
        this.yoe = yoe;
        this.location = location;
    }

    protected Nanny_old(Parcel in) {
        name = in.readString();
        gender = in.readString();
        birthday = in.readString();
        reviewScore = in.readDouble();
        yoe = in.readDouble();
        location = in.readString();
    }

    public static final Creator<Nanny_old> CREATOR = new Creator<Nanny_old>() {
        @Override
        public Nanny_old createFromParcel(Parcel in) {
            return new Nanny_old(in);
        }

        @Override
        public Nanny_old[] newArray(int size) {
            return new Nanny_old[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(birthday);
        parcel.writeDouble(reviewScore);
        parcel.writeDouble(yoe);
        parcel.writeString(location);
    }
}
