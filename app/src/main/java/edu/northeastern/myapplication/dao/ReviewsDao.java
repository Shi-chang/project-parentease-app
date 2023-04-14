package edu.northeastern.myapplication.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.Review;

public class ReviewsDao {
    private final String PATH_REVIEWS = "reviews";
    private DatabaseReference databaseReference;

    /**
     * No argument constructor for the class.
     */
    public ReviewsDao() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Appends a review to the end of the nanny's reviews in the Firebase Realtime Database.
     *
     * @param nannyId the nanny id
     * @param review  the review object
     * @return a task
     */
    public Task<Void> create(String nannyId, Review review) {
        Objects.requireNonNull(nannyId);
        Objects.requireNonNull(review);
        return databaseReference.child(PATH_REVIEWS).child(nannyId).push().setValue(review);
    }

    /**
     * Gets the reviews of the nanny.
     *
     * @param nannyId the nanny's id
     * @return teh reviews of the nanny
     */
    public Task<DataSnapshot> getReviews(String nannyId) {
        System.out.println("here 3");
        System.out.println(nannyId);
        Objects.requireNonNull(nannyId);
        return databaseReference.child(PATH_REVIEWS).child(nannyId).get();
    }
}
