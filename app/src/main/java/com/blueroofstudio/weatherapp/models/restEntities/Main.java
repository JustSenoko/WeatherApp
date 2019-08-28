package com.blueroofstudio.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Main {
    @SerializedName("temp") public float temp;
    @SerializedName("pressure") public float pressure;
    @SerializedName("humidity") public float humidity;
    @SerializedName("temp_min") public float tempMin;
    @SerializedName("temp_max") public float tempMax;
}