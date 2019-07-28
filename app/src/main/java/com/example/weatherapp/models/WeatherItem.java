package com.example.weatherapp.models;

import java.util.Date;

public class WeatherItem {

    private Date date;
    private int temperature;
    private int pressure;
    private int wind;
    private int feelsLike;
    private String weather;

    public WeatherItem(Date date, int temperature, int pressure, int wind, int feelsLike, String weather) {
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.wind = wind;
        this.feelsLike = feelsLike;
        this.weather = weather;
    }

    public Date getDate() {
        return date;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getWind() {
        return wind;
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public String getWeather() {
        return weather;
    }
}
