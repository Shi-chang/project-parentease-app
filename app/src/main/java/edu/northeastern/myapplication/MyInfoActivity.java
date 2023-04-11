package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private User currentUser;

    private ImageView browseImageView;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;

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

        currentUser = getIntent().getExtras().getParcelable("user");

        inputNameTextView = findViewById(R.id.inputNameTextView);
        inputEmailTextView = findViewById(R.id.inputEmailTextView);
        inputLocationTextView = findViewById(R.id.inputLocationTextView);

        String name = currentUser.getUsername();
        String email = currentUser.getEmail();
        String location = currentUser.getCity();

        inputNameTextView.setText(name);
        inputEmailTextView.setText(email);
        inputLocationTextView.setText(location);

        browseImageView = findViewById(R.id.browseImageView);
        browseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        nannyShareImageView = findViewById(R.id.nannyImageView);
        nannyShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, NannyshareMain.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tipsShareImageView = findViewById(R.id.tipsImageView);
        tipsShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, PostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        myAccountImageView = findViewById(R.id.myAccountImageView);
        myAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMyInfoActivity();
            }
        });
    }

    private void refreshMyInfoActivity() {
        Intent intent = new Intent(this, MyInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}