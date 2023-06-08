package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.User;

public class SignUpActivityUser extends AppCompatActivity {
    private ImageView backButton;
    private TextView proceedButton;
    private EditText firstName, lastName, EmailSignUp, password, confirmPassword, licensePlateNumber, phoneNumber;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users");

    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        backButton = (ImageView) findViewById(R.id.backButton);
        proceedButton = (TextView) findViewById(R.id.proceedButton);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        EmailSignUp = (EditText) findViewById(R.id.BadgeEmailSignup);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        licensePlateNumber = (EditText) findViewById(R.id.LicensePlate);
        phoneNumber = (EditText) findViewById(R.id.PhoneNumber);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivityUser.this, SignInUserActivity.class);
                startActivity(intent);
            }
        });
//        proceedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SignUpActivityUser.this, UserProfileActivity.class);
//                startActivity(intent);
//            }
//        });

        mAuth = FirebaseAuth.getInstance();

        proceedButton.setOnClickListener(view -> {
            createUser();
        });
    }

    private void createUser() {
        String FirstName = firstName.getText().toString().trim();
        String LastName = lastName.getText().toString();
        String Email = EmailSignUp.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmPassword.getText().toString();
        String LicensePlateNumber = licensePlateNumber.getText().toString();
        String PhoneNumber = phoneNumber.getText().toString();

        if (TextUtils.isEmpty(FirstName)) {
            firstName.setError("First name cannot be empty");
            firstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(LastName)) {
            lastName.setError("Please enter your last name.");
            lastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ConfirmPassword)) {
            confirmPassword.setError("Please confirm your password.");
            confirmPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            EmailSignUp.setError("Email cannot be empty");
            EmailSignUp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userID = task.getResult().getUser().getUid();
                    DatabaseReference userRef = databaseRef.child(userID);

                    User user = new User(FirstName, LastName, PhoneNumber, Email, LicensePlateNumber);
                    try {
                        userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivityUser.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivityUser.this, SignInUserActivity.class));
                                } else {
                                    Toast.makeText(SignUpActivityUser.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                                    Log.d("createuserErr:",task.getException().getMessage());
                                }
                            }

                        });
                    }catch(Exception e){
                        Log.d("createUserExc",e.toString());
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(SignUpActivityUser.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}