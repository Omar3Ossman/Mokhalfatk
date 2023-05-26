package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private ImageView backButton;
    private TextView proceedButton;
    private EditText firstName, lastName, EmailSignUp, password, confirmPassword;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backButton = (ImageView) findViewById(R.id.backButton);
        proceedButton = (TextView) findViewById(R.id.proceedButton);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName=(EditText) findViewById(R.id.lastName);
        EmailSignUp=(EditText) findViewById(R.id.EmailSignUp);
        password=(EditText) findViewById(R.id.password);
        confirmPassword=(EditText) findViewById(R.id.confirmPassword);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInUserActivity.class);
                startActivity(intent);
            }
        });
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        proceedButton.setOnClickListener(view ->{
            createUser();
        });
    }
    private void createUser(){
        String FirstName = firstName.getText().toString().trim();
        String LastName = lastName.getText().toString();
        String Email =  EmailSignUp.getText().toString();
        String Password= password.getText().toString();
        String ConfirmPassword= confirmPassword.getText().toString();

        if (FirstName.isEmpty()){
            firstName.setError("First name cannot be empty");
            firstName.requestFocus();
        }

        if (LastName.isEmpty()) {
            lastName.setError("Please enter your last name.");
            lastName.requestFocus();
            return;
        }

        if (ConfirmPassword.isEmpty()) {
            confirmPassword.setError("Please confirm your password.");
            confirmPassword.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(Email)){
            EmailSignUp.setError("Email cannot be empty");
            EmailSignUp.requestFocus();
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError("Email cannot be empty");
            password.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, UserActivity.class));
                    }else{
                        Toast.makeText(SignUpActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}