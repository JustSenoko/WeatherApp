package com.example.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@SuppressWarnings("ALL")
public class CurrentWeatherData {

    @SerializedName("weather") private Weather[] weather;
    @SerializedName("main") public Main main;
    @SerializedName("wind") public Wind wind;
    @SerializedName("name") public String name;
    @SerializedName("id") public int id;
    @SerializedName("message") public String message;
    public final Date date;

    public CurrentWeatherData() {
        date = new Date();
    }

    public Weather getWeather() {
        if (weather.length == 0) {
            return null;
        }
        return weather[0];
    }
}