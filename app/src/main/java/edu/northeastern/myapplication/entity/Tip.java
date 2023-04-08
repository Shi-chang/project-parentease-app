package edu.northeastern.myapplication.entity;

import java.net.URL;
import java.util.List;

/**
 * The tip class.
 */

public class Tip {
    private String tipId;
    private String userId;
    private String title;
    private URL pictureUrl;
    private String content;
    private String filter;

    public Tip() {
    }

    public Tip(String tipId, String userId, String title, URL pictureUrl, String content, String filter) {
        this.tipId = tipId;
        this.userId = userId;
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.content = content;
        this.filter = filter;
    }

    public String getTipId() {
        return tipId;
    }
    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public URL getPictureUrl() {
        return pictureUrl;
    }

    public String getContent() {
        return content;
    }

    public String getFilter() {
        return filter;
    }

}
