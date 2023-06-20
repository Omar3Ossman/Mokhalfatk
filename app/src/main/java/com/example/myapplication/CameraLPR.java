package com.example.myapplication;

import static com.example.myapplication.R.id.Camera;
import static com.example.myapplication.R.id.image_response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CameraLPR extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 201;
    ImageView selectedImage;
    TextView cameraBtn;
    private String currentPhotoPath;
    ImageButton back;
    TextView imageResponse;
    TextView SelectViolations;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        selectedImage = findViewById(R.id.imageView);
        cameraBtn = findViewById(Camera);
        imageResponse = findViewById(image_response);
        back = (ImageButton) findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraLPR.this, OfficerProfileActivity.class);
                startActivity(intent);
            }
        });
        SelectViolations = findViewById(R.id.SelectViolations);
        SelectViolations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraLPR.this, PickViolation.class);
                startActivity(intent);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    // Permission already granted, proceed with capturing the image
                    captureImage();
                } else {
                    // Request camera permission
                    requestCameraPermission();
                }

            }
        });
    }

    private void captureImage() {
        String fileName = "img";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            imageResponse.setText("loading");

            File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
            currentPhotoPath = imageFile.getAbsolutePath();
            Uri imgUri = FileProvider.getUriForFile(CameraLPR.this, "com.example.myapplication.fileprovider", imageFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            startActivityForResult(intent, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    private boolean checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.CAMERA);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){

            Bitmap bitmap =BitmapFactory.decodeFile(currentPhotoPath);
            File imageFile = convertBitmapToFile(bitmap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

            Call<LicensePlateData> call =new RetrofitObject().api.processImage(imagePart);
            call.enqueue(new Callback<LicensePlateData>() {
                @Override
                public void onResponse(Call<LicensePlateData> call, Response<LicensePlateData> response) {
                    if (response.isSuccessful()) {
                        imageResponse.setText("loading");
                        try {


                            String jsonResponse = response.body().getLicensePlateNumbers().toString();
                            // Update your TextView with the response
                            //el mafrod te parse el response

                            //  Log.e("success","" + response.body().getLicensePlateNumbers());
                            Log.e("error","SuccessError" + jsonResponse);

                            imageResponse.setText(jsonResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                            imageResponse.setText("can't detect it");

                            Log.e("error","SuccessErrpr"+ e +"dsfads"+response);
                        }
                    } else {
                        imageResponse.setText("can't detect it");
                        //hna t2ol en dih msh sort 3rbya
                        Log.e("error","SuccessErrpr"+response);
                    }
                }

                @Override
                public void onFailure(Call<LicensePlateData> call, Throwable t) {
                    imageResponse.setText("can't detect it");
                    Log.e("error","Failureerror"+ t);
                }
            });

            selectedImage.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed with capturing the image
                captureImage();
            } else {
                // Camera permission denied
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private File convertBitmapToFile(Bitmap bitmap) {
        File file = new File(getCacheDir(), "temp_image.jpg");
        try (OutputStream outputStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error","Converterror"+e);
            return null;
        }
    }







}




