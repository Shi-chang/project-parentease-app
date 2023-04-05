package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.northeastern.myapplication.nanny.NannyPostActivity;

/**
 * The post activity that redirects the user to either a posting tip activity or a posting nanny ad activity.
 */
public class PostActivity extends AppCompatActivity {
    Button btnPostATip;
    Button btnPostANannyAd;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnPostATip = findViewById(R.id.btnPostATip);
        btnPostANannyAd = findViewById(R.id.btnPostANannyAd);

        btnPostATip.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddTipActivity.class);
            startActivity(intent);
        });

        btnPostANannyAd.setOnClickListener(view -> {
            Intent intent = new Intent(this, NannyPostActivity.class);
            startActivity(intent);
        });
    }
}