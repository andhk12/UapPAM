package com.example.uappam;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

// PENAMBAHAN: Implementasi Parcelable agar objek bisa dikirim via Intent
public class Plant implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("plant_name")
    private String plantName;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private String price;

    private int imageResource; // Tetap menggunakan drawable lokal

    // PENAMBAHAN: Konstruktor untuk membuat objek baru (digunakan saat menambah data)
    public Plant(String plantName, String price, String description) {
        this.plantName = plantName;
        this.price = price;
        this.description = description;
    }

    // --- Implementasi Parcelable ---
    protected Plant(Parcel in) {
        id = in.readInt();
        plantName = in.readString();
        description = in.readString();
        price = in.readString();
        imageResource = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(plantName);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeInt(imageResource);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Plant> CREATOR = new Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };
    // --- Akhir Implementasi Parcelable ---

    // --- Getter Methods ---
    public int getId() { return id; }
    public String getPlantName() { return plantName; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public int getImageResource() {
        return R.drawable.ic_tree_logo; // Pastikan Anda punya drawable ini
    }
}