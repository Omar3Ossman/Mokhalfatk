package com.example.myapplication;

import static com.example.myapplication.R.id.scanLPR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OfficerProfileActivity extends AppCompatActivity {
    ImageView backButton;
    TextView scanLPR;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_profile);

        backButton = (ImageView) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfficerProfileActivity.this, SignInOfficerActivity.class);
                startActivity(intent);
            }
        });
        scanLPR = findViewById(R.id.scanLPR);
        scanLPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfficerProfileActivity.this, Camera.class);
                startActivity(intent);
            }
        });
    }
}
