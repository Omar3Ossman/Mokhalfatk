package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;

import Adaptors.ViolationsAdaptor;
import Models.Violations;

public class ViolationsActivity extends AppCompatActivity {
    RecyclerView.Adapter violationAdapter;
    RecyclerView violationsList;
    private ImageButton back;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violations);

        back = (ImageButton) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViolationsActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });


        violationsList = findViewById(R.id.recyclerViolations);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        violationsList = findViewById(R.id.recyclerViolations);
        violationsList.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference violationsRef = database.getReference("users/" + userId + "/violations");

        ArrayList<Violations> violationItems = new ArrayList<>();

        violationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                violationItems.clear();

                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("Log", String.valueOf(snapshot1.getValue()));
                    violationItems.add(snapshot1.getValue(Violations.class));
                }

                violationAdapter = new ViolationsAdaptor(violationItems);
                violationsList.setAdapter(violationAdapter);
                violationsList.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Failure", "Failure");
            }
        });

    }

}