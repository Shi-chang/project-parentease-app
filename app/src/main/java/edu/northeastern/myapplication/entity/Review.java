package edu.northeastern.myapplication.entity;

/**
 * The review class.
 */
public class Review {
    private String reviewId;
    private String reviewerId;
    private String reviewerName;
    private float rating;
    private String reviewContent;

    public Review() {
    }

    public Review(String reviewId, String reviewerId, String reviewerName, float rating, String reviewContent) {
        this.reviewId = reviewId;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.reviewContent = reviewContent;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public float getRating() {
        return rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", reviewerId='" + reviewerId + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", rating=" + rating +
                ", reviewContent='" + reviewContent + '\'' +
                '}';
    }
}