package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageProcessingApi {
    @Multipart
    @POST("process_image")
    Call<LicensePlateData> processImage(@Part MultipartBody.Part image);
}


class LicensePlateData {
    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("license_plate_numbers")
    private List<String> licensePlateNumbers;

    // Add getters and setters for the fields

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getLicensePlateNumbers() {
        return licensePlateNumbers;
    }

    public void setLicensePlateNumbers(List<String> licensePlateNumbers) {
        this.licensePlateNumbers = licensePlateNumbers;
    }
}
