package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;

public class SignInUserActivity extends AppCompatActivity {

    ImageView backButton;
    TextView signUpButton;
    TextView signInButton;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
   // private ProgressDialog mLoadingBar;

//law na2et eni ha-sign up as a user hadkhol el page di

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_user);

        backButton = (ImageView) findViewById(R.id.backButton);
        signUpButton = (TextView) findViewById(R.id.signUpButton);
        signInButton = (TextView) findViewById(R.id.signInButton);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        //mLoadingBar = new ProgressDialog(SignInUserActivity.this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        signInButton.setOnClickListener(view -> {
            loginUser();
        });
    }
    private void loginUser() {
        String EmailLogin = email.getText().toString();
        String passwordLogin = password.getText().toString();

        if (TextUtils.isEmpty(EmailLogin)) {
            email.setError("Email cannot be empty");
            email.requestFocus();

        } else if (TextUtils.isEmpty(passwordLogin)) {
            password.setError("Email cannot be empty");
            password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(EmailLogin, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(SignInUserActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(SignInUserActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }
    }

}