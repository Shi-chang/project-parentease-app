package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.nanny.NannyshareMain;
import edu.northeastern.myapplication.tip.AddTipActivity;

/**
 * MyInfo Activity of this app.
 */
public class MyInfoActivity extends AppCompatActivity {
    private TextView inputNameTextView;
    private TextView inputEmailTextView;
    private TextView inputLocationTextView;
    private ImageView userImageView;
    private User currentUser;

    private ImageView homeImageView;
    private TextView text_home;
    private ImageView nannyShareImageView;
    private TextView text_nanny;
    private ImageView tipsShareImageView;
    private TextView text_tips;
    private ImageView myAccountImageView;
    private TextView text_myAccount;
    private Button logoutButton;

    private FirebaseAuth mAuth;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mAuth = FirebaseAuth.getInstance();

        currentUser = getIntent().getExtras().getParcelable("user");

        inputNameTextView = findViewById(R.id.inputNameTextView);
        inputEmailTextView = findViewById(R.id.inputEmailTextView);
        inputLocationTextView = findViewById(R.id.inputLocationTextView);

        userImageView = findViewById(R.id.userImageView);
        String profileImageUrl = currentUser.getProfileImageUrl();
        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(profileImageUrl)
                    .placeholder(R.drawable.default_profile_image)
                    .into(userImageView);
        } else {
            userImageView.setImageResource(R.drawable.default_profile_image);
        }

        homeImageView = findViewById(R.id.iv_home);
        text_home = findViewById(R.id.tv_home);
        nannyShareImageView = findViewById(R.id.iv_nanny);
        text_nanny = findViewById(R.id.tv_nanny);
        tipsShareImageView = findViewById(R.id.iv_tips);
        text_tips = findViewById(R.id.tv_tips);
        myAccountImageView = findViewById(R.id.iv_account);
        text_myAccount = findViewById(R.id.tv_account);

        String name = currentUser.getUsername();
        String email = currentUser.getEmail();
        String location = currentUser.getCity();

        inputNameTextView.setText(name);
        inputEmailTextView.setText(email);
        inputLocationTextView.setText(location);

        BottomNavClickListener bottomNavClickListener = new BottomNavClickListener(this, currentUser);
        homeImageView.setOnClickListener(bottomNavClickListener);
        text_home.setOnClickListener(bottomNavClickListener);
        nannyShareImageView.setOnClickListener(bottomNavClickListener);
        text_nanny.setOnClickListener(bottomNavClickListener);
        tipsShareImageView.setOnClickListener(bottomNavClickListener);
        text_tips.setOnClickListener(bottomNavClickListener);
        myAccountImageView.setOnClickListener(bottomNavClickListener);
        text_myAccount.setOnClickListener(bottomNavClickListener);

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MyInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Refresh my info activity.
     */
    private void refreshMyInfoActivity() {
        Intent intent = new Intent(this, MyInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}