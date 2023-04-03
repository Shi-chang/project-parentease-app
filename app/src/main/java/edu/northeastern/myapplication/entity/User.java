package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * The user class.
 */
public class User implements Parcelable {
    private String username;
    private String email;
    private String city;
    private Tip[] tips;

    public User() {
    }

    public User(String username, String email, String city, Tip[] tips) {
        this.username = username;
        this.email = email;
        this.city = city;
        this.tips = tips;
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        city = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public Tip[] getTips() {
        return tips;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(city);
    }
}
