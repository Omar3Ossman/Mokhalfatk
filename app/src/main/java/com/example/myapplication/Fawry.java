package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Fawry extends AppCompatActivity {
    ImageButton back;
    TextView buttonPay,cancel;
    private EditText fawryCode1;
    private EditText fawryCode2;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userId = user.getUid();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fawry);

        buttonPay = findViewById(R.id.btnPayFawry);
        fawryCode1 = findViewById(R.id.fawrycode1);
        fawryCode2 = findViewById(R.id.fawrycode2);

        Intent intent = getIntent();
        int generatedCode = intent.getIntExtra("GeneratedCode",0);
        Log.d("TAG", "Generated code: " + generatedCode);

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
                String code1 = fawryCode1.getText().toString();
                String code2 = fawryCode2.getText().toString();

                Log.d("TAG", "onCreate: Generated Code "+ String.valueOf(generatedCode));
                Log.d("TAG", "onCreate: Code 1 "+ String.valueOf(code1));
                Log.d("TAG", "onCreate: Code 2 "+ String.valueOf(code2));
                if (!code1.equals(code2)) {
                    Toast.makeText(Fawry.this, "Codes are not matching", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (code1.equals(String.valueOf(generatedCode))) {
                    Toast.makeText(Fawry.this, "Payment successful", Toast.LENGTH_SHORT).show();
                    // Add payment logic here
                }
                    else {
                        Toast.makeText(Fawry.this, "Incorrect codes inserted", Toast.LENGTH_SHORT).show();
                        return;
                }

                HashMap<String,Object> updates = new HashMap<>();
                updates.put("status","PAID");
                violationsRef.updateChildren(updates);
                Intent intent = new Intent(Fawry.this, Congrats.class);
                startActivity(intent);
            }
        });
        cancel=findViewById(R.id.cancelpym);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Fawry.this,ViolationsActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}