package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * The nanny class.
 */
public class Nanny implements Parcelable {
    private int yoe;
    private String gender;
    private int hourlyRate;
    private List<TimeSlot> availability;
    private float ratings;
    private String introduction;

    public Nanny() {
    }

    public Nanny(int yoe, String gender, int hourlyRate, List<TimeSlot> availability, float ratings, String introduction) {
        this.yoe = yoe;
        this.gender = gender;
        this.hourlyRate = hourlyRate;
        this.availability = availability;
        this.ratings = ratings;
        this.introduction = introduction;
    }

    public int getYoe() {
        return yoe;
    }

    public String getGender() {
        return gender;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public void setAvailability(List<TimeSlot> availability) {
        this.availability = availability;
    }

    public float getRatings() {
        return ratings;
    }

    public String getIntroduction() {
        return introduction;
    }

    @Override
    public String toString() {
        return "Nanny{" +
                "yoe=" + yoe +
                ", gender='" + gender + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", availability=" + availability +
                ", ratings=" + ratings +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(yoe);
        parcel.writeString(gender);
        parcel.writeInt(hourlyRate);
        parcel.writeList(availability);
        parcel.writeFloat(ratings);
        parcel.writeString(introduction);
    }
}
