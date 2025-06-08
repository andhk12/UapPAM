package com.example.uappam;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.uappam.Api.ApiClient;
import com.example.uappam.Api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUpdateActivity extends AppCompatActivity {

    public static final String EXTRA_PLANT = "extra_plant";
    private boolean isUpdate = false;
    private Plant plant;

    private EditText etPlantName, etPlantPrice, etPlantDescription;
    private Button btnSubmit;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        Toolbar toolbar = findViewById(R.id.toolbar_add_update);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etPlantName = findViewById(R.id.et_plant_name);
        etPlantPrice = findViewById(R.id.et_plant_price);
        etPlantDescription = findViewById(R.id.et_plant_description);
        btnSubmit = findViewById(R.id.btn_submit);
        apiService = ApiClient.getClient().create(ApiService.class);

        if (getIntent().hasExtra(EXTRA_PLANT)) {
            isUpdate = true;
            plant = getIntent().getParcelableExtra(EXTRA_PLANT);
            getSupportActionBar().setTitle("Update Tanaman");
            btnSubmit.setText("Simpan");

            etPlantName.setText(plant.getPlantName());
            etPlantPrice.setText(plant.getPrice());
            etPlantDescription.setText(plant.getDescription());
        } else {
            getSupportActionBar().setTitle("Tambah Tanaman");
            btnSubmit.setText("Tambah");
        }

        btnSubmit.setOnClickListener(v -> submitData());
    }

    private void submitData() {
        String name = etPlantName.getText().toString().trim();
        String price = etPlantPrice.getText().toString().trim();
        String description = etPlantDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Plant newPlant = new Plant(name, price, description);

        if (isUpdate) {
            updatePlant(newPlant);
        } else {
            createPlant(newPlant);
        }
    }



    private void createPlant(Plant plantToCreate) {
        apiService.createPlant(plantToCreate).enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddUpdateActivity.this, "Tanaman berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke halaman sebelumnya
                } else {
                    Toast.makeText(AddUpdateActivity.this, "Gagal menambahkan. Kode: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {
                Toast.makeText(AddUpdateActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePlant(Plant plantToUpdate) {
        // PERBAIKAN: Kirim nama tanaman (getPlantName) sebagai path, bukan ID (getId).
        // Nama diambil dari objek 'plant' original sebelum diubah.
        apiService.updatePlant(plant.getPlantName(), plantToUpdate).enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddUpdateActivity.this, "Tanaman berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddUpdateActivity.this, "Gagal memperbarui. Kode: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {
                Toast.makeText(AddUpdateActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}