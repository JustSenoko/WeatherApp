package com.example.weatherapp.models.POJO;

public class WeatherForecastList {
    private int code;
    private float message;
    private City city;
    private WeatherForecast[] list;

    public int getCode() {
        return code;
    }

    public float getMessage() {
        return message;
    }

    public City getCity() {
        return city;
    }

    public WeatherForecast[] getList() {
        return list;
    }
}


