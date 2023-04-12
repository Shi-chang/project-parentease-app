package edu.northeastern.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import edu.northeastern.myapplication.authorisation.RegisterActivity;
import edu.northeastern.myapplication.dao.UserDao;
import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.utils.Utils;

/**
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private EditText login_email, login_password;
    private FirebaseAuth mAuth;
    private Button btn_login;
    private Button btn_register;
    private String cityName;
    private String userToken;

    private FirebaseMessaging firebaseMessaging;
    private FusedLocationProviderClient fusedLocationProviderClient;

    /**
     * Called when the app is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        // Gets user location permission.
        getUserLocationPermission();

        // Gets the user's registration token in FirebaseMessaging.
        firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    userToken = task.getResult();
                    return;
                }

                Toast.makeText(MainActivity.this, "Failed to get registration token", Toast.LENGTH_SHORT).show();

            }
        });

        login_email = findViewById(R.id.loginEmail);
        login_password = findViewById(R.id.loginPassword);
        btn_login = findViewById(R.id.loginBtn);
        btn_register = findViewById(R.id.goToRegisterBtn);

        btn_login.setOnClickListener(v -> {
            String email = login_email.getText().toString().trim();
            String password = login_password.getText().toString().trim();
            if (email.isEmpty()) {
                login_email.setError("Email can not be empty.");
                login_email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                login_email.setError("Please enter the valid email address.");
                login_email.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                login_password.setError("Password can not be empty.");
                login_password.requestFocus();
                return;
            }

            if (password.length() < 6) {
                login_password.setError("Length of password must be more than 6.");
                login_password.requestFocus();
                return;
            }

            // Signs in the user with the email and password.
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login failed. Try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userId = mAuth.getUid();
                // Updates the user's city name.
                UserDao userDao = new UserDao();
                if (cityName != null) {
                    userDao.updateCity(userId, cityName);
                }

                // Updates the user's token.
                if (userToken != null) {
                    userDao.updateUserToken(userId, userToken);
                }

                userDao.findUserById(userId).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        User user = task.getResult().getValue(User.class);
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("user", user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                if (task.getException() != null && task.getException() instanceof FirebaseAuthInvalidUserException) {
                    Toast.makeText(this, "No registration found with the email. Please register.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (task.getException() != null && task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        });

        btn_register.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    /**
     * Gets the user's location.
     */
    private void getUserLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCityName();
        }
    }

    /**
     * Called when the request permission activity finishes.
     *
     * @param requestCode  The request code
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *                     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCityName();
                    Toast.makeText(MainActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No permission granted.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * Gets the city that the user is current in.
     */
    private void getCityName() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        CancellationToken cancellationToken = cancellationTokenSource.getToken();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Failed to get the location.", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken).addOnSuccessListener(location -> {
            if (location == null) {
                Toast.makeText(MainActivity.this, "Failed to get the location.", Toast.LENGTH_SHORT).show();
                return;
            }
            cityName = Utils.getCityName(MainActivity.this, location);
        });
    }
}