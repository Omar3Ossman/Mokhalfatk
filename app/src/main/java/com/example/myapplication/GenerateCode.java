package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Random;

public class GenerateCode extends AppCompatActivity {

    TextView proceedFawry;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userId = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_code_popup);

        String violationID = getIntent().getStringExtra("ViolationID");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference violationsRef = database.getReference("users/" + userId + "/violations/" + violationID);

        TextView textViewGeneratedCode = findViewById(R.id.textViewGeneratedCode);

        // Generate random number with a maximum of 8 digits
        Random random = new Random();
        int randomNumber = random.nextInt(100000000);

        // Set the generated code in the TextView
        textViewGeneratedCode.setText(String.valueOf(randomNumber));

        proceedFawry = (TextView) findViewById(R.id.proceedFawry);


        //Redirect to class Fawry
        proceedFawry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Fawry activity
                Intent intent = new Intent(GenerateCode.this, Fawry.class);
                intent.putExtra("ViolationID", violationID);
                startActivity(intent);
            }
        });

    }
}
