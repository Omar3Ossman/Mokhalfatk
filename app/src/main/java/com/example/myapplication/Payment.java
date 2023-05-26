package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Payment extends AppCompatActivity {
    TextView fawry, card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        fawry = findViewById(R.id.btnPayWithFawry);
        fawry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, GenerateCode.class);
                startActivity(intent);
            }
        });
        card = findViewById(R.id.btnPayWithCard);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, Card.class);
                startActivity(intent);
            }
        });
    }
}