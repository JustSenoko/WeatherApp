package com.example.weatherapp.models.restEntities;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings({"unused"})
public class WeatherForecast {
    @SerializedName("dt") public int dt;
    @SerializedName("dt_txt") public String dateTxt;
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
    @SerializedName("wind") public Wind wind;

    public Weather getWeather() {
        return weather.length == 0 ? null : weather[0];
    }

    public Date getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd hh:mm:ss");
        try {
            return format.parse(dateTxt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
