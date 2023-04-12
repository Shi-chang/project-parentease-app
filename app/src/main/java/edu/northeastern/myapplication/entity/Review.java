package edu.northeastern.myapplication.entity;

/**
 * The review class.
 */
public class Review {
    private String reviewId;
    private String reviewerId;
    private float rating;
    private String reviewContent;

    public Review() {
    }

    public Review(String reviewId, String reviewerId, float rating, String reviewContent) {
        this.reviewId = reviewId;
        this.reviewerId = reviewerId;
        this.rating = rating;
        this.reviewContent = reviewContent;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public float getRating() {
        return rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    //TODO YE: given a nanny id, get all reviews for the nanny
}
