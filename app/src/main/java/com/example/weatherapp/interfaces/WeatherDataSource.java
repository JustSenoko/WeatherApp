package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.WeatherItem;

public interface WeatherDataSource {

    WeatherItem loadCurrentWeatherData(String city, String units);
}
