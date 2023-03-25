package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MyInfoActivity extends AppCompatActivity {
    private TextView inputNameTextView;
    private TextView inputIdTextView;
    private TextView inputGenderTextView;
    private TextView inputBrithDateTextView;
    private TextView inputLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        inputNameTextView = findViewById(R.id.inputNameTextView);
        inputIdTextView = findViewById(R.id.inputIdTextView);
        inputGenderTextView = findViewById(R.id.inputGenderTextView);
        inputBrithDateTextView = findViewById(R.id.inputBrithDateTextView);
        inputLocationTextView = findViewById(R.id.inputLocationTextView);

    }
}