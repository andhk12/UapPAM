package com.example.uappam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<Plant> plantList;
    private OnItemActionClickListener listener;

    public interface OnItemActionClickListener {
        void onDetailClick(Plant plant);
        void onDeleteClick(Plant plant, int position);
    }

    public PlantAdapter(List<Plant> plantList, OnItemActionClickListener listener) {
        this.plantList = plantList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant currentPlant = plantList.get(position);
        holder.bind(currentPlant, listener);
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    // PERUBAIKAN: Mengganti nama metode dari updateData menjadi setData agar lebih umum
    // dan sesuai dengan pemanggilan awal di Activity.
    public void setData(List<Plant> newPlantList) {
        this.plantList.clear();
        this.plantList.addAll(newPlantList);
        notifyDataSetChanged();
    }

    // PENAMBAHAN: Metode untuk menghapus item dari adapter secara aman.
    // Metode ini menangani penghapusan dari list internal dan memberitahu RecyclerView.
    public void removeItem(int position) {
        if (position >= 0 && position < plantList.size()) {
            plantList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, plantList.size());
        }
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlantImage;
        TextView tvPlantName, tvPlantPrice;
        Button btnDetail, btnHapus;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPlantImage = itemView.findViewById(R.id.iv_plant_image);
            tvPlantName = itemView.findViewById(R.id.tv_plant_name);
            tvPlantPrice = itemView.findViewById(R.id.tv_plant_price);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
        }

        public void bind(final Plant plant, final OnItemActionClickListener listener) {
            // PERUBAIKAN: Menggunakan getter yang benar 'getPlantName()' dari model Plant.
            tvPlantName.setText(plant.getPlantName());
            tvPlantPrice.setText(plant.getPrice());
            ivPlantImage.setImageResource(plant.getImageResource());

            btnDetail.setOnClickListener(v -> listener.onDetailClick(plant));
            btnHapus.setOnClickListener(v -> listener.onDeleteClick(plant, getAdapterPosition()));
        }
    }
}