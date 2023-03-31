package edu.northeastern.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    EditText registerUsername, registerPassword, registerConfirmPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword);
        btn_register = findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerUsername.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                String confirmPassword = registerConfirmPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    registerUsername.setError("Email can not be empty.");
                    registerUsername.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    registerUsername.setError("Please enter the valid email address.");
                    registerUsername.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    registerPassword.setError("Password can not be empty.");
                    registerPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    registerPassword.setError("Length of password must be more than 6.");
                    registerPassword.requestFocus();
                    return;
                }
                if (password != confirmPassword) {
                    registerConfirmPassword.setError("Password are not matching. Please try again.");
                    registerConfirmPassword.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Your are successfully register.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "You are not registered, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
