package edu.northeastern.myapplication.entity;

/**
 * This class represents the available time slot of a nanny.
 */
public class TimeSlot {
    private int startTime;
    private int endTime;
    private boolean isAvailable;
    private String clientPhoneNumber;
    private String clientAddress;

    public TimeSlot() {
    }

    public TimeSlot(int startTime, int endTime, boolean isAvailable, String clientPhoneNumber, String clientAddress) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientAddress = clientAddress;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getClientAddress() {
        return clientAddress;
    }
}
