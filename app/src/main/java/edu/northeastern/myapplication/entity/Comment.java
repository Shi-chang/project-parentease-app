package edu.northeastern.myapplication.entity;

/**
 * The comment class.
 */

public class Comment {
    private String commentId;
    private String commentatorId;
    private String tipId;
    private String content;

    public Comment() {
    }

    public Comment(String commentId, String commentatorId, String tipId, String content) {
        this.commentId = commentId;
        this.commentatorId = commentatorId;
        this.tipId = tipId;
        this.content = content;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentatorId() {
        return commentatorId;
    }

    public String getTipId() {
        return tipId;
    }

    public String getContent() {
        return content;
    }
}
