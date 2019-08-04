package com.example.weatherapp.models.pojo;

import java.util.Date;

public class CurrentWeatherData {

    private Weather[] weather;
    private Main main;
    private Wind wind;
    private String name;
    private int id;
    private final Date date;
    private String message;

    public CurrentWeatherData() {
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

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }
}
