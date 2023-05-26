package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {
    ImageView backButton;
    ImageView viewViolationsButton;
    TextView signUpUserButton;
    TextView signUpOfficerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, SignInUserActivity.class);
                startActivity(intent);
            }
        });

        viewViolationsButton = findViewById(R.id.imageView3);
        viewViolationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, ViolationsActivity.class);
                startActivity(intent);
            }
        });

//        signUpUserButton = findViewById(R.id.signUpUserButton);
//        signUpUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserProfileActivity.this, SignUpActivityUser.class);
//                startActivity(intent);
//            }
//        });
//
//        signUpOfficerButton = findViewById(R.id.signUpOfficerButton);
//        signUpOfficerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserProfileActivity.this, SignUpActivityOfficer.class);
//                startActivity(intent);
//            }
//        });
    }
}
