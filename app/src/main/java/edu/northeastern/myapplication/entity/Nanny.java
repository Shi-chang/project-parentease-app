package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

/**
 * The nanny class.
 */
public class Nanny implements Parcelable {
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

    protected Nanny(Parcel in) {
        nannyId = in.readString();
        yoe = in.readInt();
        gender = in.readString();
        hourlyRate = in.readInt();
        ratings = in.readFloat();
        introduction = in.readString();
        username = in.readString();
        city = in.readString();
    }

    public static final Creator<Nanny> CREATOR = new Creator<Nanny>() {
        @Override
        public Nanny createFromParcel(Parcel in) {
            return new Nanny(in);
        }

        @Override
        public Nanny[] newArray(int size) {
            return new Nanny[size];
        }
    };

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

    public void setRatings(float ratings) {
        this.ratings = ratings;
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
        parcel.writeString(nannyId);
        parcel.writeInt(yoe);
        parcel.writeString(gender);
        parcel.writeInt(hourlyRate);
        parcel.writeFloat(ratings);
        parcel.writeString(introduction);
        parcel.writeString(username);
        parcel.writeString(city);


        //TODO ADD TIMESLOT

    }

    public static Comparator<Nanny> rateAsc = new Comparator<Nanny>() {
        @Override
        public int compare(Nanny nanny1, Nanny nanny2) {
            float rate1 = nanny1.getHourlyRate();
            float rate2 = nanny2.getHourlyRate();
            return Float.compare(rate1, rate2);
        }
    };

    public static Comparator<Nanny> ratingDesc = new Comparator<Nanny>() {
        @Override
        public int compare(Nanny nanny1, Nanny nanny2) {
            float rating1 = nanny1.getRatings();
            float rating2 = nanny2.getRatings();
            return Float.compare(rating1, rating2);
        }
    };
}
