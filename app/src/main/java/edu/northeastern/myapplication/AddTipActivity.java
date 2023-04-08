package edu.northeastern.myapplication;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import edu.northeastern.myapplication.authorisation.RegisterActivity;
import edu.northeastern.myapplication.entity.Comment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        getLoadImagePermission();

        addPictureButton = findViewById(R.id.addPictureButton);
        addPictureImageView = findViewById(R.id.addPictureImageView);
        addTitleEditText = findViewById(R.id.addTitleEditText);
        addContentEditText = findViewById(R.id.addContentEditText);
        pediatriciansCheckBox = findViewById(R.id.pediatriciansCheckBox);
        daycareCheckBox = findViewById(R.id.daycareCheckBox);
        eventInfoCheckBox = findViewById(R.id.eventInfoCheckBox);
        postButton = findViewById(R.id.postButton);
        storage = FirebaseStorage.getInstance();
    }

    private void getLoadImagePermission() {
        if (ActivityCompat.checkSelfPermission(AddTipActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(AddTipActivity.this, "permission request.", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(AddTipActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

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

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

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
                                System.out.println("URL  -----------  " + downloadUrl);
                                // Do something with the URL (e.g., save it to a database)
                            }
                        });
                    } else {
                        // Handle the error
                        Exception exception = task.getException();
                        // ...
                    }
                }
            });
        }
    }

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
        filter.trim();
        if (filter.equals("")) {
            filter += "Other";
        }
        // get tipId
        UUID uuid = UUID.randomUUID();
        String tipId = uuid.toString();
        // get the current user Id

        // get comments, is empty
        //Comment[] comments = new Comment[];

        // get the urL
        String pictureUrl = downloadUrl;
        // create a tip
        // Tip tip = new Tip(tipId, userId, title, pictureUrl, content, filter, comments);




        // post the tip to database under currentUser

        // post the tip to Tip table

        // go back to the home page

    }

}