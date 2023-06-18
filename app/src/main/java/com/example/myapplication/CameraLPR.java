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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CameraLPR extends AppCompatActivity {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private static final int REQUEST_IMAGE_CAPTURE = 103;
    ImageView selectedImage;
    TextView cameraBtn;
    private String currentPhotoPath;
    ImageButton back;
    TextView imageResponse;


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

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fileName = "img";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
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
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){

            Bitmap bitmap =BitmapFactory.decodeFile(currentPhotoPath);
            File imageFile = convertBitmapToFile(bitmap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

            Call<ResponseBody> call =new RetrofitObject().api.processImage(imagePart);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String jsonResponse = response.body().string();
                            // Update your TextView with the response
                            //el mafrod te parse el response
                            imageResponse.setText(jsonResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("error","SuccessErrpr"+ e +"dsfads"+response);
                        }
                    } else {
                        //hna t2ol en dih msh sort 3rbya
                        Log.e("error","SuccessErrpr"+response);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("error","Failureerror"+ t);
                }
            });

            selectedImage.setImageBitmap(bitmap);

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




