package com.example.myapplication;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageProcessingApi {
    @Multipart
    @POST("process_image")
    Call<ResponseBody> processImage(@Part MultipartBody.Part image);
}
