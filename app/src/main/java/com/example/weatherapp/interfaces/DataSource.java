package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.WeatherItem;

import java.util.Date;

public interface DataSource {

    boolean findCity(String cityName);
    boolean loadCityWeather(String cityName);
    WeatherItem getWeather(String city, Date date);
}
