package edu.northeastern.myapplication.tip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.northeastern.myapplication.HomeActivity;
import edu.northeastern.myapplication.MyInfoActivity;
import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.CommentsDao;
import edu.northeastern.myapplication.entity.Comment;
import edu.northeastern.myapplication.entity.Tip;
import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.nanny.NannyshareMain;
import edu.northeastern.myapplication.utils.Utils;

/**
 * The Single Tip activity of this app.
 */
public class SingleTipActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private ImageView pictureImageView;
    private TextView titleTextView;
    private TextView contentTextView;
    private EditText inputCommentTextView;
    private Button addCommentButton;
    private RecyclerView commentRecyclerView;
    private Tip currentTip;
    private String tipId;
    FirebaseStorage storage;
    private DatabaseReference mDatabase;
    private List<Comment> commentList;
    private CommentsAdapter commentsAdapter;
    private RecyclerView.LayoutManager commentsLayoutManager;
    private FirebaseAuth mAuth;
    private String commentContent;
    private String commentId;
    private String commentatorId;
    private User commentator;
    private String commentatorName;
    private Comment comment;
    private ImageView browseImageView;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;
    private static String SERVER_KEY;
    private String tipOwnerToken;

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

        createNotificationChannel();

        // Gets the server key.
        SERVER_KEY = "key=" + Utils.getProperties(this).getProperty("SERVER_KEY");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        userNameTextView = findViewById(R.id.userNameTextView);
        pictureImageView = findViewById(R.id.pictureImageView);
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        inputCommentTextView = findViewById(R.id.inputCommentTextView);
        addCommentButton = findViewById(R.id.addCommentButton);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);

        // initial the comment recycler view
        commentsAdapter = new CommentsAdapter(this, commentList);
        commentsLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(commentsLayoutManager);
        commentRecyclerView.setAdapter(commentsAdapter);
        commentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // get the current tip from the intent
        currentTip = getIntent().getExtras().getParcelable("tip");

        // get the tip creator's userId
        String userId = currentTip.getUserId();

        // use userId find the tip creator, get the user name and token
        mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SingleTipActivity.this, "Failed to get the user.", Toast.LENGTH_LONG).show();
                    return;
                }
                User user = task.getResult().getValue(User.class);
                String userName = user.getUsername();
                userNameTextView.setText("Tip From: " + userName);
                tipOwnerToken = user.getUserToken();
            }
        });

        // get URL and then get the picture loading
        storage = FirebaseStorage.getInstance();
        String pictureUrl = currentTip.getPictureUrl();
        Glide.with(this)
                .load(pictureUrl)
                .into(pictureImageView);

        // get the title of the tip and load the title
        String title = currentTip.getTitle();
        titleTextView.setText("Title: " + title);

        // get the content of the tip and load the content
        String content = currentTip.getContent();
        contentTextView.setText("Content: " + content);

        // get the tipId and use tipId to find the comments
        tipId = currentTip.getTipId();
        mDatabase.child("comments").child(tipId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SingleTipActivity.this, "Failed to get the comments.", Toast.LENGTH_LONG).show();
                    return;
                }
                commentList = new ArrayList<>();
                Iterable<DataSnapshot> commentDataList = task.getResult().getChildren();
                for (DataSnapshot commentSnapshot : commentDataList) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    commentList.add(comment);
                }
                commentsAdapter.setCommentList(commentList);
            }
        });

        // on add comment button click
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentContent = inputCommentTextView.getText().toString();
                if (commentContent == "") {
                    Toast.makeText(SingleTipActivity.this, "Please add a comment first.", Toast.LENGTH_LONG).show();
                } else {
                    UUID uuid = UUID.randomUUID();
                    commentId = uuid.toString();
                    mAuth = FirebaseAuth.getInstance();
                    commentatorId = mAuth.getUid();
                    mDatabase.child("users").child(commentatorId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SingleTipActivity.this, "Failed to find the current user.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            commentator = task.getResult().getValue(User.class);
                            commentatorName = commentator.getUsername();
                            // create a comment
                            comment = new Comment(commentId, commentatorId, tipId, commentatorName, commentContent);
                            commentList.add(comment);
                            // display the comment in recycler view
                            commentsAdapter.setCommentList(commentList);
                            // upload the comment to firebase
                            mDatabase.child("comments").child(tipId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    List<Comment> newCommentList = new ArrayList<>();
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SingleTipActivity.this, "Failed to add comment.", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    Iterable<DataSnapshot> commentDataList = task.getResult().getChildren();
                                    for (DataSnapshot commentSnapshot : commentDataList) {
                                        Comment commentData = commentSnapshot.getValue(Comment.class);
                                        newCommentList.add(commentData);
                                    }
                                    newCommentList.add(comment);
                                    CommentsDao commentsDao = new CommentsDao();
                                    commentsDao.updateComments(tipId, newCommentList);
                                    Toast.makeText(SingleTipActivity.this, "Add comment successfully.", Toast.LENGTH_LONG).show();
                                    // send the notification to tip owner
                                    sendMessageToTipOwner(tipOwnerToken, comment);
                                }
                            });
                        }
                    });
                }
            }
        });

        browseImageView = findViewById(R.id.browseImageView);
        browseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTipActivity.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", commentator);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        nannyShareImageView = findViewById(R.id.nannyImageView);
        nannyShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTipActivity.this, NannyshareMain.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", commentator);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tipsShareImageView = findViewById(R.id.tipsImageView);
        tipsShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTipActivity.this, PostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", commentator);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        myAccountImageView = findViewById(R.id.myAccountImageView);
        myAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTipActivity.this, MyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", commentator);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * Create notification channel.
     */
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String id = getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // create new channel
            NotificationChannel channel = new NotificationChannel(id, name, importance);

            // Set description.
            channel.setDescription(description);

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Send message to the tip owner.
     *
     * @param tipOwnerToken the token from the tip owner
     * @param comment the comment being made
     */
    private void sendMessageToTipOwner(String tipOwnerToken, Comment comment) {
        // Get notification json file
        JSONObject notification = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject payload = new JSONObject();

        String notificationTitle = "New Comment From " + comment.getCommentatorName();
        String notificationBody = comment.getContent();

        try {
            notification.put("title", notificationTitle);
            notification.put("body", notificationBody);
            data.put("title:", "data:" + notificationTitle);
            data.put("body", "data:" + notificationBody);
            payload.put("to", tipOwnerToken);
            payload.put("priority", "high");
            payload.put("notification", notification);
            payload.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creates a new thread to run the network activity to avoid NetworkOnMainThreadException.
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String resp = fcmHttpConnection(SERVER_KEY, payload);
            }
        });

        newThread.start();
    }

    /**
     * FCM http connection method.
     *
     * @param serverToken the firebase server token
     * @param jsonObject the jsonObject need to be sent
     * @return return a string
     */
    private static String fcmHttpConnection(String serverToken, JSONObject jsonObject) {
        // Loads a payload
        try {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", serverToken);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = connection.getInputStream();

            System.out.println(convertStreamToString(inputStream));

            return convertStreamToString(inputStream);
        } catch (IOException e) {
            return "NULL";
        }
    }

    /**
     * Convert stream to a string.
     *
     * @param inputStream the input stream
     * @return a string
     */
    private static String convertStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while ((len = bufferedReader.readLine()) != null) {
                stringBuilder.append(len);
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ",\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}