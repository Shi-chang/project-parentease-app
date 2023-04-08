package edu.northeastern.myapplication.nanny;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.entity.Review;
import edu.northeastern.myapplication.entity.User;

public class NannyPostActivity extends AppCompatActivity {
    private TextView nannyNameTv2;
    private Spinner genderSpinner;

    private List<String> genderList;
    private ArrayAdapter<String> genderAdapter;

    private User user;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nanny_post);

        user = getIntent().getExtras().getParcelable("user");

        nannyNameTv2 = findViewById(R.id.nannyNameTv2);
        nannyNameTv2.setText(user.getUsername());

        genderSpinner = findViewById(R.id.genderSpinner);
        genderList = new ArrayList<>();
        List<String> genderStringsList = Arrays.asList("Select", "Female", "Male", "Non-binary");
        genderList.addAll(genderStringsList);
        genderAdapter = new ArrayAdapter<>(NannyPostActivity.this, android.R.layout.simple_spinner_dropdown_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = genderSpinner.getSelectedItem().toString();
                if (selectedGender.equals("Gender")) {
                    Toast.makeText(NannyPostActivity.this, "Gender not selected.", Toast.LENGTH_SHORT).show();
                    return;
                }

                gender = selectedGender;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

//    private String userId;
//    private int yoe;
//    private String gender;
//    private Review[] reviews;
//    private float hourlyRate;
//    private Map<String, Boolean> availability;
}