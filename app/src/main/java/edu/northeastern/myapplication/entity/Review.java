package edu.northeastern.myapplication.entity;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.myapplication.HomeActivity;
import edu.northeastern.myapplication.dao.ReviewsDao;

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
}

//TODO YE: given a nanny id, get all reviews for the nanny

// Put the following code inside the body of a method
// Puts the reviews of the nanny into the review list.
//    List<Review> reviewList = new ArrayList<>();
//    ReviewsDao reviewsDao = new ReviewsDao();
//        reviewsDao.getReviews("replaceWithRealNannyId").addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<DataSnapshot> task) {
//            if (!task.isSuccessful()) {
//                Toast.makeText(HomeActivity.this, "Failed to get reviews.", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            DataSnapshot taskResult = task.getResult();
//            if (!taskResult.exists()) {
//                Toast.makeText(HomeActivity.this, "The nanny has no review.", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            for (DataSnapshot dataSnapshot : taskResult.getChildren()) {
//                reviewList.add(dataSnapshot.getValue(Review.class));
//            }
//        }
//    });

// TODO: YE: given a nanny id and a review object, put the review to the nanny
//ReviewsDao reviewsDao = new ReviewsDao();
//    Review newReview = new Review("replace1", "replace2", 0, "replace3");
//        reviewsDao.create("replaceWithRealNannyId", newReview).addOnCompleteListener(new OnCompleteListener<Void>() {
//@Override
//public void onComplete(@NonNull Task<Void> task) {
//        if (!task.isSuccessful()) {
//        Toast.makeText(HomeActivity.this, "Failed to post the review.", Toast.LENGTH_SHORT).show();
//        return;
//        }
//
//        Toast.makeText(HomeActivity.this, "Review posted successfully.", Toast.LENGTH_SHORT).show();
//        }
//        });
