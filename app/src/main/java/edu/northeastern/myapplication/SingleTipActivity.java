package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleTipActivity extends AppCompatActivity {

    private TextView userNameTextView;

    private ImageView pictureImageView;

    private TextView titleTextView;

    private TextView textTextView;

    private RecyclerView commentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tip);

        userNameTextView = findViewById(R.id.userNameTextView);
        pictureImageView = findViewById(R.id.pictureImageView);
        titleTextView = findViewById(R.id.titleTextView);
        textTextView = findViewById(R.id.textTextView);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);



    }
}