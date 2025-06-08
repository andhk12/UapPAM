package com.example.uappam;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PlantResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Plant> data;

    public String getMessage() {
        return message;
    }

    public List<Plant> getData() {
        return data;
    }
}