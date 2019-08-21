package com.example.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Weather {
    @SerializedName("main") public String main;
    @SerializedName("description") public String description;
    @SerializedName("id") public int id;
    @SerializedName("icon") public String icon;
}
