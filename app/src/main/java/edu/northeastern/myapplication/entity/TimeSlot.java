package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * This class represents the available time slot of a nanny.
 */
public class TimeSlot implements Parcelable {
    private Date date;
    private int startTime;
    private String clientId;
    private String clientName;
    private String clientPhoneNumber;
    private String clientAddress;

    public TimeSlot() {
    }

    public TimeSlot(Date date, int startTime, String clientId, String clientName, String clientPhoneNumber, String clientAddress) {
        this.date = date;
        this.startTime = startTime;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientAddress = clientAddress;
    }

    protected TimeSlot(Parcel in) {
        date = new Date(in.readLong());
        startTime = in.readInt();
        clientId = in.readString();
        clientName = in.readString();
        clientPhoneNumber = in.readString();
        clientAddress = in.readString();
    }

    public static final Creator<TimeSlot> CREATOR = new Creator<TimeSlot>() {
        @Override
        public TimeSlot createFromParcel(Parcel in) {
            return new TimeSlot(in);
        }

        @Override
        public TimeSlot[] newArray(int size) {
            return new TimeSlot[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientPhoneNumber='" + clientPhoneNumber + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeInt(startTime);
        dest.writeString(clientId);
        dest.writeString(clientName);
        dest.writeString(clientPhoneNumber);
        dest.writeString(clientAddress);
    }
}
