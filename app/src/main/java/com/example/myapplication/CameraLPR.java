package com.example.myapplication;

import static com.example.myapplication.R.id.cambtn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class CameraLPR extends AppCompatActivity {
    private String currphotopath;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        findViewById(cambtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename="photo";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File imageFile=File.createTempFile(filename,".jpg", storageDirectory );
                    currphotopath=imageFile.getAbsolutePath();
                    Uri imageUri=FileProvider.getUriForFile(CameraLPR.this, "com.example.dummycam.fileprovider",imageFile);
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri );
                    startActivityForResult(intent,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            Bitmap bitmap= BitmapFactory.decodeFile(currphotopath);
            ImageView imageView=findViewById(R.id.camimg);
            imageView.setImageBitmap(bitmap);
        }
    }
}

