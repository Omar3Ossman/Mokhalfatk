package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    ImageView btnUser;
    ImageView btnPO;
    ImageButton back;

    //Login as User or Police Officer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnUser = findViewById(R.id.ImgUser);
        btnPO = findViewById(R.id.btnPO);
        back = (ImageButton) findViewById(R.id.backButton1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView signUpOfficer = findViewById(R.id.signUpOfficerButton);
        signUpOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, SignUpActivityOfficer.class);
                startActivity(intent);
            }
        });

        TextView signUpUser = findViewById(R.id.signUpUserButton);
        signUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, SignUpActivityUser.class);
                startActivity(intent);
            }
        });


        btnUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, SignInUserActivity.class);
                startActivity(intent);
            }
        });

        btnPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, SignInOfficerActivity.class);
                startActivity(intent);
            }
        });

    }
}