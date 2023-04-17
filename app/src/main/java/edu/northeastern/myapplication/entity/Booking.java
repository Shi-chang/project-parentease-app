package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Tha booking class.
 */
public class Booking implements Parcelable {
    private String date;
    private int startTime;
    private String nannyId;
    private String nannyName;
    private int nannyHourlyRate;
    private String nannyGender;

    public Booking() {
    }

    public Booking(String date, int startTime, String nannyId, String nannyName, int nannyHourlyRate, String nannyGender) {
        this.date = date;
        this.startTime = startTime;
        this.nannyId = nannyId;
        this.nannyName = nannyName;
        this.nannyHourlyRate = nannyHourlyRate;
        this.nannyGender = nannyGender;
    }

    protected Booking(Parcel in) {
        date = in.readString();
        startTime = in.readInt();
        nannyId = in.readString();
        nannyName = in.readString();
        nannyHourlyRate = in.readInt();
        nannyGender = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public String getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public String getNannyId() {
        return nannyId;
    }

    public String getNannyName() {
        return nannyName;
    }

    public int getNannyHourlyRate() {
        return nannyHourlyRate;
    }

    public String getNannyGender() {
        return nannyGender;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "date='" + date + '\'' +
                ", startTime=" + startTime +
                ", nannyId='" + nannyId + '\'' +
                ", nannyName='" + nannyName + '\'' +
                ", nannyHourlyRate=" + nannyHourlyRate +
                ", nannyGender='" + nannyGender + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeInt(startTime);
        dest.writeString(nannyId);
        dest.writeString(nannyName);
        dest.writeInt(nannyHourlyRate);
        dest.writeString(nannyGender);
    }
}
