package edu.northeastern.myapplication.nanny;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.NannyDao;
import edu.northeastern.myapplication.dao.ReviewsDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.Review;
import edu.northeastern.myapplication.entity.User;

public class NannyPostReview extends AppCompatActivity {
    private TextView et_nannyReview;
    private RatingBar ratingBar;
    private Button btnPostANannyReview;
    boolean isRatingBarClicked;
    private Nanny nanny;
    private NannyDao nannyDao;
    private User user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nanny_review);

        user = getIntent().getExtras().getParcelable("user");
        mAuth = FirebaseAuth.getInstance();

        et_nannyReview = findViewById(R.id.et_nannyReview);
        ratingBar = findViewById(R.id.ratingBar);
        btnPostANannyReview = findViewById(R.id.btnPostANannyReview);
        isRatingBarClicked = false;

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                isRatingBarClicked = true;
            }
        });

        // Gets the current nanny.
        nanny = getIntent().getExtras().getParcelable("nanny");
        String nannyId = nanny.getNannyId();
        nannyDao = new NannyDao();
        nannyDao.findNannyById(nannyId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(NannyPostReview.this, "Failed to get the nanny.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot dataSnapshot = task.getResult();
                if (!dataSnapshot.exists()) {
                    Toast.makeText(NannyPostReview.this, "This nanny does not exist.", Toast.LENGTH_SHORT).show();
                    return;
                }

                nanny = dataSnapshot.getValue(Nanny.class);

                btnPostANannyReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et_nannyReview.getText().toString().strip().length() == 0) {
                            Toast.makeText(NannyPostReview.this, "Please input your review.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!isRatingBarClicked) {
                            Toast.makeText(NannyPostReview.this, "Please provide rating.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Gets a unique id for the review.
                        UUID uuid = UUID.randomUUID();
                        String reviewId = uuid.toString();

                        // Creates a review, puts the review to the database.
                        Review review = new Review(reviewId, mAuth.getUid(), user.getUsername(), ratingBar.getRating(), et_nannyReview.getText().toString());
                        ReviewsDao reviewsDao = new ReviewsDao();
                        reviewsDao.create(nanny.getNannyId(), review).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // Updates the nanny's ratings.
                                List<Review> reviewList = new ArrayList<>();

                                reviewsDao.getReviews(nanny.getNannyId()).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                                        if (!task.isSuccessful()) {
                                            Toast.makeText(NannyPostReview.this, "Failed to get reviews.", Toast.LENGTH_LONG).show();
                                            return;
                                        }

                                        DataSnapshot taskResult = task.getResult();
                                        for (DataSnapshot dataSnapshot : taskResult.getChildren()) {
                                            reviewList.add(dataSnapshot.getValue(Review.class));
                                        }

                                        float sumOfRatings = 0;
                                        for (int i = 0; i < reviewList.size(); i++) {
                                            sumOfRatings += reviewList.get(i).getRating();
                                        }

                                        float updatedRatings = sumOfRatings / reviewList.size();
                                        nanny.setRatings(updatedRatings);

                                        NannyDao nannyDao = new NannyDao();
                                        nannyDao.update(nanny.getNannyId(), nanny).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(NannyPostReview.this, "Nanny's ratings updated successfully.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(NannyPostReview.this, NannyshareSingle.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putParcelable("user", user);
                                                bundle.putParcelable("nanny", nanny);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}

