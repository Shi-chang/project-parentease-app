package edu.northeastern.myapplication.entity;

import java.util.Date;

/**
 * This class represents the available time slot of a nanny.
 */
public class TimeSlot {
    private Date date;
    private int startTime;
    private String clientPhoneNumber;
    private String clientAddress;

    public TimeSlot() {
    }

    public TimeSlot(Date date, int startTime, String clientPhoneNumber, String clientAddress) {
        this.date = date;
        this.startTime = startTime;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientAddress = clientAddress;
    }

    public Date getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", clientPhoneNumber='" + clientPhoneNumber + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                '}';
    }
}
