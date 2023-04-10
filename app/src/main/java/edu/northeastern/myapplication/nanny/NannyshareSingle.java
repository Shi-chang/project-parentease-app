package edu.northeastern.myapplication.nanny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.northeastern.myapplication.MyInfoActivity;
import edu.northeastern.myapplication.Nanny;
import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.User;

public class NannyshareSingle extends AppCompatActivity {
    private TextView tv_name;
    private TextView tv_reviewScore;
    private ImageView myAccountImageView;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private Button btn_availability;
    private Button btn_postReview;
    private User currentUser;
    private Nanny currentNanny;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nannyshare_single);

        currentUser = getIntent().getExtras().getParcelable("user");
        currentNanny = getIntent().getExtras().getParcelable("nanny");

        String name = currentNanny.getName();
        Double reviewScore = currentNanny.getReviewScore();

        tv_name = findViewById(R.id.tv_nanny_name);
        tv_name.setText(name);

        // USE THIS FORMAT for star: android:text="★ 0.00"
        tv_reviewScore = findViewById(R.id.tv_star);
        tv_reviewScore.setText("★ "+ reviewScore.toString());

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
        nannyShareImageView = findViewById(R.id.tv_nanny);
        nannyShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NannyshareSingle.this, NannyshareMain.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tipsShareImageView = findViewById(R.id.tv_tips);
        tipsShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NannyshareSingle.this, PostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        myAccountImageView = findViewById(R.id.tv_myAccount);
        myAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NannyshareSingle.this, MyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }


}