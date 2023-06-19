package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfileActivity extends AppCompatActivity {
    ImageView viewViolationsButton;
    TextView signUpUserButton,signUpOfficerButton,logout;
    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Display the retrieved data in your TextViews or EditTexts
        TextView firstNameTextView = findViewById(R.id.firstName);
        TextView lastNameTextView = findViewById(R.id.lastName);
        TextView phoneNumberTextView = findViewById(R.id.profilePhone);
        TextView emailTextView = findViewById(R.id.BadgeEmailSignup);
        TextView licensePlateNumberTextView = findViewById(R.id.badgeNumber);
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data
                    String userId = dataSnapshot.child("ID").getValue(String.class);
                    String firstName = dataSnapshot.child("firstName").getValue(String.class);
                    String lastName = dataSnapshot.child("lastName").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String licensePlateNumber = dataSnapshot.child("licensePlateNumber").getValue(String.class);

                    firstNameTextView.setText(firstName);
                    lastNameTextView.setText(lastName);
                    phoneNumberTextView.setText(phoneNumber);
                    emailTextView.setText(email);
                    licensePlateNumberTextView.setText(licensePlateNumber);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
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
        logout=findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(UserProfileActivity.this,SignInUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
