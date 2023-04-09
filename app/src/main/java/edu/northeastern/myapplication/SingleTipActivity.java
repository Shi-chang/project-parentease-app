package edu.northeastern.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.net.URL;

import edu.northeastern.myapplication.entity.Comment;
import edu.northeastern.myapplication.entity.Tip;
import edu.northeastern.myapplication.entity.User;

/**
 * The Single Tip activity of this app.
 */
public class SingleTipActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private ImageView pictureImageView;
    private TextView titleTextView;
    private TextView contentTextView;
    private RecyclerView commentRecyclerView;
    private Tip currentTip;
    FirebaseStorage storage;
    private String tipId;

    private DatabaseReference mDatabase;

    /**
     * Called when the Single Tip activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tip);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        userNameTextView = findViewById(R.id.userNameTextView);
        pictureImageView = findViewById(R.id.pictureImageView);
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);

        // get the current tipId from the intent
        Bundle extras = getIntent().getExtras();
        tipId = extras.getString("tipId");

        // use tipId find the tip, get the content
        mDatabase.child("tips").child(tipId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SingleTipActivity.this, "Failed to get the tip.", Toast.LENGTH_LONG).show();
                    return;
                }
                currentTip = task.getResult().getValue(Tip.class);
                Toast.makeText(SingleTipActivity.this, "Get tip successfully.", Toast.LENGTH_LONG).show();
            }
        });

        // Gets the current user name from the intent.
        // Bundle extras = getIntent().getExtras();
        // userName = extras.getString("userName");
        // userNameTextView.setText("User Name: " + userName);

        // get URL and then get the picture loading
        storage = FirebaseStorage.getInstance();
        URL pictureUrl = currentTip.getPictureUrl();
        Glide.with(this)
                .load(pictureUrl)
                .into(pictureImageView);

        // get the title of the tip and load the title
        String title = currentTip.getTitle();
        titleTextView.setText(title);

        // get the content of the tip and load the content
        String content = currentTip.getContent();
        contentTextView.setText(content);


        // get the comments from the tipId and initial the comment recycler view


        // add comment to the current tip of the view


        // send the comment to database


        // send a notification to the current tip user id


    }
}