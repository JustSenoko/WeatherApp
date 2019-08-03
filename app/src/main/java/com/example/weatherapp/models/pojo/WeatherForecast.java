package com.example.weatherapp.models.pojo;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherForecast {
    private int dt;
    private String dt_txt;
    private Main main;
    private Weather[] weather;
    private Wind wind;

    public int getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public Weather getWeather() {
        return weather.length == 0 ? null : weather[0];
    }

    public Wind getWind() {
        return wind;
    }

    public Date getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd hh:mm:ss");
        try {
            return format.parse(dt_txt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
