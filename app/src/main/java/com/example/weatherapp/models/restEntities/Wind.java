package com.example.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Wind {
    @SerializedName("code") public float speed;
    @SerializedName("code") private float deg;
}
