package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class GenerateCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_code_popup);

        TextView textViewGeneratedCode = findViewById(R.id.textViewGeneratedCode);

        // Generate random number with a maximum of 8 digits
        Random random = new Random();
        int randomNumber = random.nextInt(100000000);

        // Set the generated code in the TextView
        textViewGeneratedCode.setText(String.valueOf(randomNumber));

        //Redirect to class Fawry
        textViewGeneratedCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Fawry activity
                Intent intent = new Intent(GenerateCode.this, Fawry.class);
                startActivity(intent);
            }
        });

    }
}
