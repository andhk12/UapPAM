package com.example.uappam.Api; // Buat package baru 'network' agar rapi

import com.google.gson.Gson; // Import Gson
import com.google.gson.GsonBuilder; // Import GsonBuilder
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://uappam.kuncipintu.my.id/";
    private static Retrofit retrofit = null;

    // PENAMBAHAN: Membuat instance Gson yang lenient
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // PERUBAHAN: Gunakan Gson yang sudah dibuat
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}