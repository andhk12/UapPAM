package com.example.uappam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uappam.Api.ApiClient;
import com.example.uappam.Api.ApiService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements PlantAdapter.OnItemActionClickListener {

    private RecyclerView rvPlants;
    private PlantAdapter plantAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvPlants = findViewById(R.id.rv_plants);
        Button btnTambahList = findViewById(R.id.btn_tambah_list);

        apiService = ApiClient.getClient().create(ApiService.class);

        setupRecyclerView();

        btnTambahList.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddUpdateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPlants();
    }

    private void setupRecyclerView() {
        plantAdapter = new PlantAdapter(new ArrayList<>(), this);
        rvPlants.setLayoutManager(new LinearLayoutManager(this));
        rvPlants.setAdapter(plantAdapter);
    }

    private void fetchPlants() {
        Call<PlantResponse> call = apiService.getAllPlants();
        call.enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Plant> plantList = response.body().getData();
                    if (plantList != null) {
                        plantAdapter.setData(plantList);
                    } else {
                        Toast.makeText(HomeActivity.this, "Data tanaman tidak ditemukan.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Gagal mengambil data. Kode: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HomeActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDetailClick(Plant plant) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PLANT, plant);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Plant plant, int position) {
        Call<Void> call = apiService.deletePlant(plant.getPlantName());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, plant.getPlantName() + " berhasil dihapus", Toast.LENGTH_SHORT).show();
                    plantAdapter.removeItem(position);
                } else {
                    Toast.makeText(HomeActivity.this, "Gagal menghapus. Kode: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}