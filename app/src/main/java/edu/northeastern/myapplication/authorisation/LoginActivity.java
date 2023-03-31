package edu.northeastern.myapplication.authorisation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    private EditText login_username, login_password;
    private TextView newUser;
    private FirebaseAuth mAuth;
    private Button btn_login;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_username = findViewById(R.id.loginUsername);
        login_password = findViewById(R.id.loginPassword);
        btn_login = findViewById(R.id.loginBtn);
        btn_register = findViewById(R.id.goToRegisterBtn);
        newUser = findViewById(R.id.newUserTv);

        mAuth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(v -> {
            String email = login_username.getText().toString().trim();
            String password = login_password.getText().toString().trim();
            if (email.isEmpty()) {
                login_username.setError("Email can not be empty.");
                login_username.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                login_username.setError("Please enter the valid email address.");
                login_username.requestFocus();
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

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Please check your email or password.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        newUser.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        btn_register.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}

