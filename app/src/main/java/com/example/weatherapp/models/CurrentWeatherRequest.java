package com.example.weatherapp.models;

public class CurrentWeatherRequest {
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private String name;

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
}
