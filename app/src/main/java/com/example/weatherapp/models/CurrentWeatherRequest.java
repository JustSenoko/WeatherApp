package com.example.weatherapp.models;

import com.example.weatherapp.models.weather.Main;
import com.example.weatherapp.models.weather.Weather;
import com.example.weatherapp.models.weather.Wind;

import java.util.Date;

public class CurrentWeatherRequest {

    private static final int CODE_OK = 200;

    private Weather[] weather;
    private Main main;
    private Wind wind;
    private String name;
    private Date date;
    private int cod;
    private String message;

    public CurrentWeatherRequest() {
        date = new Date();
    }

    public Weather getWeather() {
        if (weather.length == 0) {
            return null;
        }
        return weather[0];
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }

    public boolean LoadedSuccessful() {
        return cod == CODE_OK;
    }

    public String getMessage() {
        return message;
    }
}
