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

public class SingleTipActivity extends AppCompatActivity {

    private TextView userNameTextView;

    private ImageView pictureImageView;

    private TextView titleTextView;

    private TextView contentTextView;

    private RecyclerView commentRecyclerView;

    private Tip currentTip;

    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tip);

        storage = FirebaseStorage.getInstance();

        currentTip = getIntent().getExtras().getParcelable("currentTip");

        userNameTextView = findViewById(R.id.userNameTextView);
        pictureImageView = findViewById(R.id.pictureImageView);
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);

        // get the info from the current tip


        // get userId then get and load user name
        // String userName = currentTip.getUsername();


        // get URL and then get the picture loading
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
        // Comment[] comments = currentTip.getComments();


        // add comment to the current tip of the view


        // send the comment to database


        // send a notification to the current tip user id


    }
}