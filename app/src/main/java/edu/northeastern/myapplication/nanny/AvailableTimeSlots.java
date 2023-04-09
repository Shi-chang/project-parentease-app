package edu.northeastern.myapplication.nanny;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import edu.northeastern.myapplication.R;

public class AvailableTimeSlots extends AppCompatActivity {
    TextView dateTv1;
    Button btnTs1;
    Button btnTs2;
    Button btnTs3;
    Button btnTs4;
    Button btnTs5;
    Button btnTs6;
    Button btnTs7;
    Button btnTs8;
    Button btnConfirmAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_time_slots);

        dateTv1 = findViewById(R.id.dateTv1);
        btnTs1 = findViewById(R.id.btnTs1);
        btnTs2 = findViewById(R.id.btnTs2);
        btnTs3 = findViewById(R.id.btnTs3);
        btnTs4 = findViewById(R.id.btnTs4);
        btnTs5 = findViewById(R.id.btnTs5);
        btnTs6 = findViewById(R.id.btnTs6);
        btnTs7 = findViewById(R.id.btnTs7);
        btnTs8 = findViewById(R.id.btnTs8);
        btnConfirmAvailability = findViewById(R.id.btnConfirmAvailability);

        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("dayOfMonth", 0);
        dateTv1.setText(year + "-" + month + "-" + day);


    }
}