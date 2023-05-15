package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViolationsActivity extends AppCompatActivity {
TextView unpaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violations);

        /*unpaid = findViewById(R.id.paid);
        unpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViolationsActivity.this, Payment.class);
                startActivity(intent);
            }
        });*/

    }
}