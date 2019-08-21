package com.example.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

public class WeatherForecastList {
    @SerializedName("code") public int code;
    @SerializedName("message") public float message;
    @SerializedName("city") public City city;
    @SerializedName("list") public WeatherForecast[] list;
}


