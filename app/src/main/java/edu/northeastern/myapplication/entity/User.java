package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * The user class.
 */
public class User implements Parcelable {
    private String username;
    private String email;
    private String city;
    private List<Tip> tips;

    public User() {
    }

    public User(String username, String email, String city, List<Tip> tips) {
        this.username = username;
        this.email = email;
        this.city = city;
        this.tips = tips;
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        city = in.readString();
        tips = in.readArrayList(Tip.class.getClassLoader());
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

    public List<Tip> getTips() {
        return tips;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
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
        dest.writeList(tips);
    }
}
