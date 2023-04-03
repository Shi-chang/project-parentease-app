package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.northeastern.myapplication.entity.User;

public class MyInfoActivity extends AppCompatActivity {
    private TextView inputNameTextView;
    private TextView inputEmailTextView;
    private TextView inputLocationTextView;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        currentUser = getIntent().getExtras().getParcelable("currentUser");

        inputNameTextView = findViewById(R.id.inputNameTextView);
        inputEmailTextView = findViewById(R.id.inputEmailTextView);
        inputLocationTextView = findViewById(R.id.inputLocationTextView);

        String name = currentUser.getUsername();
        String email = currentUser.getEmail();
        String location = currentUser.getCity();

        inputNameTextView.setText(name);
        inputEmailTextView.setText(email);
        inputLocationTextView.setText(location);
    }
}