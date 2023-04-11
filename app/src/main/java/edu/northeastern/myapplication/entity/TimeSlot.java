package edu.northeastern.myapplication.entity;

import java.util.Date;

/**
 * This class represents the available time slot of a nanny.
 */
public class TimeSlot {
    private Date date;
    private int startTime;
    private String clientId;
    private String clientPhoneNumber;
    private String clientAddress;

    public TimeSlot() {
    }

    public TimeSlot(Date date, int startTime, String clientId, String clientPhoneNumber, String clientAddress) {
        this.date = date;
        this.startTime = startTime;
        this.clientId = clientId;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientAddress = clientAddress;
    }

    public Date getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public String getClientId() {
        return clientId;
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
                ", clientPhoneNumber='" + clientPhoneNumber + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                '}';
    }
}
