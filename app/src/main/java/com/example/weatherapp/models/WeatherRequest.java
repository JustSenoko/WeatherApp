package com.example.weatherapp.models;

public class WeatherRequest {
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private String name;

    public Weather[] getWeather() {
        return weather;
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
}
