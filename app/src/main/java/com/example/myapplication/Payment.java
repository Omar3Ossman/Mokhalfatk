package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Payment extends AppCompatActivity {
    TextView fawry, card;
    ImageButton back;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userId = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        back = (ImageButton) findViewById(R.id.backButton1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, ViolationsActivity.class);
                startActivity(intent);
            }
        });

        String violationID = getIntent().getStringExtra("Violation ID");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference violationsRef = database.getReference("users/" + userId + "/violations/" + violationID);
        Log.d("paid", violationsRef.toString());



        fawry = findViewById(R.id.btnPayWithFawry);
        fawry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                violationsRef.child("status").setValue("paid");
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