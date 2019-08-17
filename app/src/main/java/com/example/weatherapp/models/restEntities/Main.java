package com.example.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Main {
    @SerializedName("temp") public float temp;
    @SerializedName("pressure") public float pressure;
    @SerializedName("humidity") public float humidity;
    @SerializedName("temp_min") public float temp_min;
    @SerializedName("temp_max") public float temp_max;
}
