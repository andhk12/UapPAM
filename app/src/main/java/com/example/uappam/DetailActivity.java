package com.example.uappam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_PLANT = "extra_plant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView ivPlantImage = findViewById(R.id.iv_plant_detail_image);
        TextView tvPlantName = findViewById(R.id.tv_plant_detail_name);
        TextView tvPlantPrice = findViewById(R.id.tv_plant_detail_price);
        TextView tvPlantDescription = findViewById(R.id.tv_plant_detail_description);
        Button btnUpdate = findViewById(R.id.btn_update);

        Plant plant = getIntent().getParcelableExtra(EXTRA_PLANT);

        if (plant != null) {
            getSupportActionBar().setTitle(plant.getPlantName());
            ivPlantImage.setImageResource(plant.getImageResource());
            tvPlantName.setText(plant.getPlantName());
            tvPlantPrice.setText(plant.getPrice());
            tvPlantDescription.setText(plant.getDescription());

            btnUpdate.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, AddUpdateActivity.class);
                intent.putExtra(AddUpdateActivity.EXTRA_PLANT, plant);
                startActivity(intent);
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}