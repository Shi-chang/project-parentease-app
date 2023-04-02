package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddTipActivity extends AppCompatActivity {

    private Button addPictureButton;
    private EditText addTitleEditText;
    private EditText addContentEditText;
    private CheckBox pediatriciansCheckBox;
    private CheckBox daycareCheckBox;
    private CheckBox eventInfoCheckBox;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        addPictureButton = findViewById(R.id.addPictureButton);
        addTitleEditText = findViewById(R.id.addTitleEditText);
        addContentEditText = findViewById(R.id.addContentEditText);
        pediatriciansCheckBox = findViewById(R.id.pediatriciansCheckBox);
        daycareCheckBox = findViewById(R.id.daycareCheckBox);
        eventInfoCheckBox = findViewById(R.id.eventInfoCheckBox);
        postButton = findViewById(R.id.postButton);

    }
}