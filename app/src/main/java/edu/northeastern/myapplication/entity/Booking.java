package edu.northeastern.myapplication.entity;

import java.util.Date;

/**
 * Tha booking class.
 */
public class Booking {
    private Date date;
    private String nannyId;
    private String nannyName;
    private int nannyHourlyRate;
    private String nannyGender;

    public Booking() {
    }

    public Booking(Date date, String nannyId, String nannyName, int nannyHourlyRate, String nannyGender) {
        this.date = date;
        this.nannyId = nannyId;
        this.nannyName = nannyName;
        this.nannyHourlyRate = nannyHourlyRate;
        this.nannyGender = nannyGender;
    }

    public Date getDate() {
        return date;
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
        return "Booking{" + "date=" + date + ", nannyId='" + nannyId + '\'' + ", nannyName='" + nannyName + '\'' + ", nannyHourlyRate=" + nannyHourlyRate + ", nannyGender='" + nannyGender + '\'' + '}';
    }
}
