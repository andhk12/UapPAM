package com.example.uappam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
    // private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvPlants = findViewById(R.id.rv_plants);
        Button btnTambahList = findViewById(R.id.btn_tambah_list);
        // progressBar = findViewById(R.id.progress_bar);

        apiService = ApiClient.getClient().create(ApiService.class);

        setupRecyclerView();

        // PENYESUAIAN: Listener tombol tambah sekarang membuka AddUpdateActivity
        btnTambahList.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddUpdateActivity.class);
            startActivity(intent);
        });
    }

    // PENAMBAHAN: Memanggil fetchPlants() di onResume.
    // Ini memastikan list akan selalu refresh setelah menambah, mengupdate,
    // atau kembali dari activity lain.
    @Override
    protected void onResume() {
        super.onResume();
        fetchPlants();
    }

    private void setupRecyclerView() {
        // Inisialisasi adapter dengan list kosong terlebih dahulu
        plantAdapter = new PlantAdapter(new ArrayList<>(), this);
        rvPlants.setLayoutManager(new LinearLayoutManager(this));
        rvPlants.setAdapter(plantAdapter);
    }

    private void fetchPlants() {
        // progressBar.setVisibility(View.VISIBLE);
        Call<List<Plant>> call = apiService.getAllPlants();
        call.enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                // progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    // Menggunakan metode 'setData' di adapter untuk memperbarui UI
                    plantAdapter.setData(response.body());
                } else {
                    Toast.makeText(HomeActivity.this, "Gagal mengambil data. Kode: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HomeActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    // PENYESUAIAN: Implementasi onDetailClick untuk membuka DetailActivity
    @Override
    public void onDetailClick(Plant plant) {
        Intent intent = new Intent(this, DetailActivity.class);
        // Mengirim seluruh objek Plant yang sudah Parcelable
        intent.putExtra(DetailActivity.EXTRA_PLANT, plant);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Plant plant, int position) {
        // progressBar.setVisibility(View.VISIBLE);
        Call<Void> call = apiService.deletePlant(plant.getPlantName());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, plant.getPlantName() + " berhasil dihapus", Toast.LENGTH_SHORT).show();
                    // Memanggil metode di adapter untuk menghapus item dari UI secara aman
                    plantAdapter.removeItem(position);
                } else {
                    Toast.makeText(HomeActivity.this, "Gagal menghapus. Kode: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}