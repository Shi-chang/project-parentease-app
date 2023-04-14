package edu.northeastern.myapplication.nanny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.myapplication.BottomNavClickListener;
import edu.northeastern.myapplication.HomeActivity;
import edu.northeastern.myapplication.MyInfoActivity;
import edu.northeastern.myapplication.Nanny_old;
import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.RecyclerViewInterface;
import edu.northeastern.myapplication.dao.ReviewsDao;
import edu.northeastern.myapplication.entity.Nanny;
import edu.northeastern.myapplication.entity.Review;
import edu.northeastern.myapplication.entity.User;

public class NannyshareSingle extends AppCompatActivity implements RecyclerViewInterface {
    private TextView tv_name;
    private TextView tv_reviewScore;
    private TextView tv_yoe;
    private TextView tv_city;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;
    private ImageView homeImageView;
    private TextView text_home;
    private TextView text_nanny;
    private TextView text_tips;
    private TextView text_myAccount;
    private Button btn_availability;
    private Button btn_postReview;
    private User currentUser;
    private Nanny currentNanny;
    private RecyclerView recyclerView;
    private ReviewCardAdapter adapter;
    private ArrayList<Review> reviewArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nannyshare_single);

        currentUser = getIntent().getExtras().getParcelable("user");
        currentNanny = getIntent().getExtras().getParcelable("nanny");
        System.out.println("hihihi rceived: " + currentNanny.toString());

        String name = currentNanny.getUsername();
        System.out.println("NannyshareSingle username: "+name);
        Float reviewScore = currentNanny.getRatings();
        Integer yoe = currentNanny.getYoe();
        String city = currentNanny.getCity();
        System.out.println("NannyshareSingle city: "+city);

        tv_name = findViewById(R.id.tv_nanny_name);
        tv_name.setText("Name: " + name);

        //ratings: USE THIS FORMAT for star: android:text="★ 0.00"
        tv_reviewScore = findViewById(R.id.tv_star);
        tv_reviewScore.setText("★ "+ reviewScore.toString());

        //yoe
        tv_yoe = findViewById(R.id.tv_nannyYOE);
        tv_yoe.setText("YOE: " + yoe);

        //city
        tv_city = findViewById(R.id.tv_nannyLocation);
        tv_city.setText("City: " + city);

        //reviews
        InitializeCardView();

        //button link to check availability
        btn_availability = findViewById(R.id.btn_book);
        btn_availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NannyshareSingle.this, NannyBookingInformation.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);

                Bundle nannyBundle = new Bundle();
                nannyBundle.putParcelable("nanny", currentNanny);
                intent.putExtras(nannyBundle);


                startActivity(intent);
            }
        });

        //button link to POST REVIEW
        btn_postReview = findViewById(R.id.btn_postReview);
        btn_postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NannyshareSingle.this, NannyPostReview.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //button link to other activity

        homeImageView = findViewById(R.id.iv_home);
        text_home = findViewById(R.id.tv_home);
        nannyShareImageView = findViewById(R.id.iv_nanny);
        text_nanny = findViewById(R.id.tv_nanny);
        tipsShareImageView = findViewById(R.id.iv_tips);
        text_tips = findViewById(R.id.tv_tips);
        myAccountImageView = findViewById(R.id.iv_account);
        text_myAccount = findViewById(R.id.tv_account);

        BottomNavClickListener bottomNavClickListener = new BottomNavClickListener(this, currentUser);
        homeImageView.setOnClickListener(bottomNavClickListener);
        text_home.setOnClickListener(bottomNavClickListener);
        nannyShareImageView.setOnClickListener(bottomNavClickListener);
        text_nanny.setOnClickListener(bottomNavClickListener);
        tipsShareImageView.setOnClickListener(bottomNavClickListener);
        text_tips.setOnClickListener(bottomNavClickListener);
        myAccountImageView.setOnClickListener(bottomNavClickListener);
        text_myAccount.setOnClickListener(bottomNavClickListener);

    }

    private void InitializeCardView() {
        recyclerView = findViewById(R.id.rv_nannyReview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewArrayList = new ArrayList<Review>();

        ReviewsDao reviewsDao = new ReviewsDao();
        reviewsDao.getReviews(currentNanny.getNannyId()).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(NannyshareSingle.this, "Failed to get reviews.", Toast.LENGTH_LONG).show();
                    return;
                }

                DataSnapshot taskResult = task.getResult();
                if (!taskResult.exists()) {
                    Toast.makeText(NannyshareSingle.this, "The nanny has no review.", Toast.LENGTH_LONG).show();
                    return;
                }

                for (DataSnapshot dataSnapshot : taskResult.getChildren()) {
                    reviewArrayList.add(dataSnapshot.getValue(Review.class));
                    //Review review = new Review("123","12345", (float) 4.80, "pretent i have ratings");
                    //reviewArrayList.add(review);
                    System.out.println("review size: "+reviewArrayList.size());
                    adapter = new ReviewCardAdapter(NannyshareSingle.this, reviewArrayList, NannyshareSingle.this);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }
        });


    }


    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(NannyshareSingle.this, NannyBookingInformation.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        intent.putExtras(bundle);

        Bundle nannyBundle = new Bundle();
        nannyBundle.putParcelable("nanny", currentNanny);
        intent.putExtras(nannyBundle);


        startActivity(intent);

    }
}