package edu.northeastern.myapplication.entity;

import java.net.URL;

/**
 * The tip class.
 */

public class Tip {
    private String tipId;
    private String title;
    private URL pictureUrl;
    private String content;
    private String filter;
    private Comment[] comments;

    public Tip() {
    }

    public Tip(String tipId, String title, URL pictureUrl, String content, String filter, Comment[] comments) {
        this.tipId = tipId;
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.content = content;
        this.filter = filter;
        this.comments = comments;
    }

    public String getTipId() {
        return tipId;
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

    public Comment[] getComments() {
        return comments;
    }
}
