package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.nanny.NannyInformation;
import edu.northeastern.myapplication.nanny.NannyshareMain;
import edu.northeastern.myapplication.tip.AddTipActivity;

/**
 * The post activity that redirects the user to either a posting tip activity or a posting nanny ad activity.
 */
public class PostActivity extends AppCompatActivity {
    Button btnPostATip;
    Button btnPostANannyAd;
    User user;
    private ImageView browseImageView;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        user = getIntent().getExtras().getParcelable("user");

        btnPostATip = findViewById(R.id.btnPostATip);
        btnPostANannyAd = findViewById(R.id.btnPostANannyAd);

        btnPostATip.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddTipActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        btnPostANannyAd.setOnClickListener(view -> {
            Intent intent = new Intent(this, NannyInformation.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        browseImageView = findViewById(R.id.browseImageView);
        browseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        nannyShareImageView = findViewById(R.id.nannyImageView);
        nannyShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, NannyshareMain.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tipsShareImageView = findViewById(R.id.tipsImageView);
        tipsShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostActivity();
            }
        });

        myAccountImageView = findViewById(R.id.myAccountImageView);
        myAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, MyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void refreshPostActivity() {
        Intent intent = new Intent(this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}