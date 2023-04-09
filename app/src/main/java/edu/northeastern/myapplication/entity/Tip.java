package edu.northeastern.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.net.URL;
import java.util.List;

/**
 * The tip class.
 */

public class Tip implements Parcelable {
    private String tipId;
    private String userId;
    private String title;
    private String pictureUrl;
    private String content;
    private String filter;

    public Tip() {
    }

    public Tip(String tipId, String userId, String title, String pictureUrl, String content, String filter) {
        this.tipId = tipId;
        this.userId = userId;
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.content = content;
        this.filter = filter;
    }

    protected Tip(Parcel in) {
        tipId = in.readString();
        userId = in.readString();
        title = in.readString();
        pictureUrl = in.readString();
        content = in.readString();
        filter = in.readString();
    }

    public static final Creator<Tip> CREATOR = new Creator<Tip>() {
        @Override
        public Tip createFromParcel(Parcel in) {
            return new Tip(in);
        }

        @Override
        public Tip[] newArray(int size) {
            return new Tip[size];
        }
    };

    public String getTipId() {
        return tipId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getContent() {
        return content;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(tipId);
        dest.writeString(userId);
        dest.writeString(title);
        dest.writeString(pictureUrl);
        dest.writeString(content);
        dest.writeString(filter);
    }
}
