package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInOfficerActivity extends AppCompatActivity {
    private EditText BadgeEmail;
    private EditText Password;
    private TextView buttonSignin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_police);

    BadgeEmail = findViewById(R.id.BadgeEmail);
    Password = findViewById(R.id.password);
    buttonSignin = findViewById(R.id.signInButton);

    firebaseAuth = FirebaseAuth.getInstance();

        buttonSignin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    });
}

    private void login() {
        // Retrieve badge number and password from user input
        String badgeNumber = BadgeEmail.getText().toString();
        String password = Password.getText().toString();

        // Validate badge number format using regex
        String regex = "\\d{5}"; // Assuming badge number is 5 digits only
        boolean isBadgeNumberValid = badgeNumber.matches(regex);

        if (isBadgeNumberValid) {
            String email = badgeNumber + "@traffic.gov.eg";

            // Proceed with Firebase authentication
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Authentication success, navigate to the next activity
                                Toast.makeText(SignInOfficerActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                // TODO: Add code to navigate to the next activity
                            } else {
                                // Authentication failed, display error message
                                Toast.makeText(SignInOfficerActivity.this, "Authentication failed, Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Show an error message to the user or handle invalid format case
            Toast.makeText(this, "Invalid badge number format", Toast.LENGTH_SHORT).show();
        }
    }
}











