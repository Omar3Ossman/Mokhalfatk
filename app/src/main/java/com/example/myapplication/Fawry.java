package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Fawry extends AppCompatActivity {
    ImageButton back;
    TextView buttonPay;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userId = user.getUid();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fawry);

        buttonPay = findViewById(R.id.btnPayFawry);

        String violationID = getIntent().getStringExtra("ViolationID");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference violationsRef = database.getReference("users/" + userId + "/violations/" + violationID);

        back = (ImageButton) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Fawry.this, Payment.class);
                startActivity(intent);
            }
        });

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> updates = new HashMap<>();
                updates.put("status","PAID");
                violationsRef.updateChildren(updates);
                Intent intent = new Intent(Fawry.this, ViolationsActivity.class);
                startActivity(intent);
            }
        });
    }
}