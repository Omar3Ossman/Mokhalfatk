package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://15.237.119.22:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ImageProcessingApi api = retrofit.create(ImageProcessingApi.class);
}
