package edu.northeastern.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;

import java.net.URL;

import edu.northeastern.myapplication.entity.Comment;
import edu.northeastern.myapplication.entity.Tip;

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

        userNameTextView = findViewById(R.id.userNameTextView);
        pictureImageView = findViewById(R.id.pictureImageView);
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);

        // get tipId, userName, title, pictureUrl from previous activity
        // use tipId find the tip, get the content
        // user tipId find the comments list


        // Gets the current user name from the intent.
        // Bundle extras = getIntent().getExtras();
        // userName = extras.getString("userName");
        // userNameTextView.setText("User Name: " + userName);

        // Gets the current title from the intent.
        // Bundle extras = getIntent().getExtras();
        // title = extras.getString("title");
        // titleTextView.setText("Title: " + title);

        // Jumps to SendStickersActivity.
        // Intent intent = new Intent(RegisterActivity.this, SendStickersActivity.class);
        // intent.putExtra("userName", userName);
        // startActivity(intent);


        // get URL and then get the picture loading
        storage = FirebaseStorage.getInstance();
        URL pictureUrl = currentTip.getPictureUrl();
        Glide.with(this)
                .load(pictureUrl)
                .into(pictureImageView);

        // get the title of the tip and load the title
        // String title = currentTip.getTitle();
        // titleTextView.setText(title);

        // get the content of the tip and load the content
        // String content = currentTip.getContent();
        // contentTextView.setText(content);


        // get the comments from the tipId and initial the comment recycler view


        // add comment to the current tip of the view


        // send the comment to database


        // send a notification to the current tip user id


    }
}