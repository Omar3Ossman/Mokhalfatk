package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignInUserActivity extends AppCompatActivity {

    ImageView backButton;
    TextView signUpButton;
    TextView signInButton;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_user);

        backButton = findViewById(R.id.backButton);
        signInButton = findViewById(R.id.signInButton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInUserActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the loginUser() method to handle sign-in
                loginUser();
//
//                Intent intent = new Intent(SignInUserActivity.this, UserProfileActivity.class);
//                startActivity(intent);
            }
        });

    }

    private void loginUser() {
        String emailLogin = email.getText().toString();
        String passwordLogin = password.getText().toString();

        if (TextUtils.isEmpty(emailLogin)) {
            email.setError("Email cannot be empty");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordLogin)) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignInUserActivity.this, "User signed in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInUserActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Finish the current activity to prevent going back to the sign-in screen
                } else {
                    Objects.requireNonNull(task.getException()).printStackTrace();
                    Toast.makeText(SignInUserActivity.this, "Sign-in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
