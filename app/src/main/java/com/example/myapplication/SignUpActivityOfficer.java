package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivityOfficer extends AppCompatActivity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextBadgeEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextPhoneNumber;
    private EditText editTextBadgeNumber;
    private TextView buttonSignUp;
    private FirebaseAuth firebaseAuth;
    private TextView Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_officer);
        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);
        editTextBadgeEmail = findViewById(R.id.BadgeEmailSignup);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        editTextPhoneNumber = findViewById(R.id.PhoneNumber);
        editTextBadgeNumber = findViewById(R.id.BadgeNo);
        buttonSignUp = findViewById(R.id.proceedButton);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivityOfficer.this, SignInOfficerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signUp() {
        // Retrieve user inputs from the EditText fields
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String badgeEmail = editTextBadgeEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String badgeNumber = editTextBadgeNumber.getText().toString();

        // Perform input validation
        if (firstName.isEmpty() || lastName.isEmpty() || badgeEmail.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty() ||
                phoneNumber.isEmpty() || badgeNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with Firebase authentication
        String email = badgeNumber + "@traffic.gov.eg";
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Signup success, navigate to the next activity
                            Toast.makeText(SignUpActivityOfficer.this, "Signup successful", Toast.LENGTH_SHORT).show();
                            // TODO: Add code to navigate to the next activity
                        } else {
                            // Signup failed, display error message
                            Toast.makeText(SignUpActivityOfficer.this, "Signup failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}