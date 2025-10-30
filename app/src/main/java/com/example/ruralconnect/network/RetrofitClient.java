package com.example.ruralconnect.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String BASE_URL = "http://192.168.0.157:8080/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Use Gson to parse JSON
                    .build();
        }
        return retrofit;
    }

    // A helper method to get the ApiService
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}