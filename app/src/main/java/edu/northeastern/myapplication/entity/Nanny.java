package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * The nanny class.
 */
public class Nanny implements Parcelable {
public class Nanny {
    private String nannyId;
    private int yoe;
    private String gender;
    private int hourlyRate;
    private List<TimeSlot> availability;
    private float ratings;
    private String introduction;
    private String username;
    private String city;

    public Nanny() {
    }

    public Nanny(String nannyId, int yoe, String gender, int hourlyRate, List<TimeSlot> availability, float ratings, String introduction, String username, String city) {
        this.nannyId = nannyId;
        this.yoe = yoe;
        this.gender = gender;
        this.hourlyRate = hourlyRate;
        this.availability = availability;
        this.ratings = ratings;
        this.introduction = introduction;
        this.username = username;
        this.city = city;
    }

    public String getNannyId() {
        return nannyId;
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

    public String getUsername() {
        return username;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Nanny{" +
                "nannyId='" + nannyId + '\'' +
                ", yoe=" + yoe +
                ", gender='" + gender + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", availability=" + availability +
                ", ratings=" + ratings +
                ", introduction='" + introduction + '\'' +
                ", username='" + username + '\'' +
                ", city='" + city + '\'' +
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
