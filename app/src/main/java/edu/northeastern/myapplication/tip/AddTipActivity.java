package edu.northeastern.myapplication.tip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.northeastern.myapplication.HomeActivity;
import edu.northeastern.myapplication.MyInfoActivity;
import edu.northeastern.myapplication.PostActivity;
import edu.northeastern.myapplication.R;
import edu.northeastern.myapplication.dao.TipsDao;
import edu.northeastern.myapplication.dao.UserDao;
import edu.northeastern.myapplication.entity.Tip;
import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.nanny.NannyshareMain;

/**
 * The Add Tip activity of this app.
 */
public class AddTipActivity extends AppCompatActivity {

    private Button addPictureButton;
    private ImageView addPictureImageView;
    private EditText addTitleEditText;
    private EditText addContentEditText;
    private CheckBox pediatriciansCheckBox;
    private CheckBox daycareCheckBox;
    private CheckBox eventInfoCheckBox;
    private Button postButton;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;
    FirebaseStorage storage;
    String downloadUrl;
    private DatabaseReference mDatabase;
    private ImageView browseImageView;
    private ImageView nannyShareImageView;
    private ImageView tipsShareImageView;
    private ImageView myAccountImageView;
    private User user;


    /**
     * Called when the Add Tip activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        getLoadImagePermission();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = getIntent().getExtras().getParcelable("user");

        addPictureButton = findViewById(R.id.addPictureButton);
        addPictureImageView = findViewById(R.id.addPictureImageView);
        addTitleEditText = findViewById(R.id.addTitleEditText);
        addContentEditText = findViewById(R.id.addContentEditText);
        pediatriciansCheckBox = findViewById(R.id.pediatriciansCheckBox);
        daycareCheckBox = findViewById(R.id.daycareCheckBox);
        eventInfoCheckBox = findViewById(R.id.eventInfoCheckBox);
        postButton = findViewById(R.id.postButton);
        storage = FirebaseStorage.getInstance();

        browseImageView = findViewById(R.id.browseImageView);
        browseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTipActivity.this, HomeActivity.class);
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
                Intent intent = new Intent(AddTipActivity.this, NannyshareMain.class);
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
                Intent intent = new Intent(AddTipActivity.this, PostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        myAccountImageView = findViewById(R.id.myAccountImageView);
        myAccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTipActivity.this, MyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    /**
     * Get the permission of image load from the device.
     */
    private void getLoadImagePermission() {
        if (ActivityCompat.checkSelfPermission(AddTipActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(AddTipActivity.this, "Permission request.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(AddTipActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * Get the permission request result.
     *
     * @param requestCode The request code passed in the method
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddTipActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTipActivity.this, "No permission granted.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * Select the image from the device.
     *
     * @param view the view of this activity
     */
    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * Select the image and get the result.
     *
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            addPictureImageView.setImageURI(selectedImageUri);

            // Get a reference to the Firebase Storage location where the image will be saved
            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());

            // Upload the image file to Firebase Storage
            UploadTask uploadTask = imageRef.putFile(selectedImageUri);

            // Listen for state changes, errors, and completion of the upload.
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Get the download URL for the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Get the URL as a string
                                downloadUrl = uri.toString();
                            }
                        });
                    } else {
                        // Handle the error
                        Exception exception = task.getException();
                    }
                }
            });
        }
    }

    /**
     * Post the tip to the firebase.
     *
     * @param view the Add Tip activity view
     */
    public void postTip(View view) {
        // get title
        if (addTitleEditText.getText().equals("")) {
            Toast.makeText(AddTipActivity.this, "Please add title", Toast.LENGTH_SHORT).show();
            return;
        }
        String title = addTitleEditText.getText().toString();
        // get content
        if (addContentEditText.getText().equals("")) {
            Toast.makeText(AddTipActivity.this, "Please add content", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = addContentEditText.getText().toString();
        // get the filter
        String filter = "";
        if (pediatriciansCheckBox.isChecked()) {
            filter = "Pediatricians ";
        }
        if (daycareCheckBox.isChecked()) {
            filter += "Daycare ";
        }
        if (eventInfoCheckBox.isChecked()) {
            filter += "Event Info ";
        }
        filter = filter.trim();
        if (filter.equals("")) {
            filter += "Others";
        }
        // get tipId
        UUID uuid = UUID.randomUUID();
        String tipId = uuid.toString();
        // get the current user Id
        String userId = user.getUserId();
        // get userName
        String userName = user.getUsername();
        // create a tip
        Tip tip = new Tip(tipId, userId, userName, title, downloadUrl, content, filter);
        // post the tip to firebase
        mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AddTipActivity.this, "Failed to add the tip.", Toast.LENGTH_LONG).show();
                    return;
                }

                User user = task.getResult().getValue(User.class);

                List<Tip> tips = user.getTips();
                if (tips == null) {
                    tips = new ArrayList<>();
                }

                tips.add(tip);
                user.setTips(tips);

                UserDao userDao = new UserDao();
                userDao.updateUser(userId, user);

                TipsDao tipsDao = new TipsDao();
                tipsDao.create(tipId, tip);

                Toast.makeText(AddTipActivity.this, "Add Tip successfully.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddTipActivity.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}