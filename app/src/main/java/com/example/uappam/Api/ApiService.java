package com.example.uappam.Api;

import com.example.uappam.Plant; // Import model Plant Anda
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Endpoint: {{baseURL}}/plant/all
    @GET("plant/all")
    Call<List<Plant>> getAllPlants();

    // Endpoint: {{baseURL}}/plant/{name}
    @DELETE("plant/{name}")
    Call<Void> deletePlant(@Path("name") String plantName);

    // --- Endpoint untuk prompt selanjutnya ---
     @POST("plant/new")
     Call<Plant> createPlant(@Body Plant plant);

     @PUT("plant/{name}")
     Call<Plant> updatePlant(@Path("name") String plantName, @Body Plant plant);
}