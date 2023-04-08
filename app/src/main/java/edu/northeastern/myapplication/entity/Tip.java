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
    private List<Comment> comments;

    public Tip() {
    }

    public Tip(String tipId, String userId, String title, URL pictureUrl, String content, String filter, List<Comment> comments) {
        this.tipId = tipId;
        this.userId = userId;
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.content = content;
        this.filter = filter;
        this.comments = comments;
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

    public List<Comment> getComments() {
        return comments;
    }
}
