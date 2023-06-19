package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OfficerProfileActivity extends AppCompatActivity {
    TextView scanLPR;
    private FirebaseAuth mAuth;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_profile);
        // Display the retrieved data in your TextViews or EditTexts
        TextView firstNameTextView = findViewById(R.id.firstName);
        TextView lastNameTextView = findViewById(R.id.lastName);
        TextView phoneNumberTextView = findViewById(R.id.profilePhone);
        TextView badgeNumberTextView = findViewById(R.id.badgeNumber);
        TextView emailTextView = findViewById(R.id.BadgeEmailSignup);
        mAuth = FirebaseAuth.getInstance();
        String officerId = mAuth.getCurrentUser().getUid();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("officers").child(officerId);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve officer data
                    String firstName = dataSnapshot.child("firstName").getValue(String.class);
                    String lastName = dataSnapshot.child("lastName").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String badgeNumber = dataSnapshot.child("badgeNumber").getValue(String.class);
                    String officerId = dataSnapshot.child("officerId").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);


                    firstNameTextView.setText(firstName);
                    lastNameTextView.setText(lastName);
                    phoneNumberTextView.setText(phoneNumber);
                    badgeNumberTextView.setText(badgeNumber);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });


        scanLPR = findViewById(R.id.scanLPR);
        scanLPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfficerProfileActivity.this, CameraLPR.class);
                startActivity(intent);
            }
        });
    }
}
