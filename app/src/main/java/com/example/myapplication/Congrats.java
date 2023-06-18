package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Congrats extends AppCompatActivity {
    TextView Return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congrats_popup);

        Return= (TextView) findViewById(R.id.ReturnToHomepage);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Congrats.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}