package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.WeatherItem;

import java.util.List;

public interface WeatherDataSource {

    WeatherItem loadCurrentWeatherData(String city, String units);
    List<WeatherItem> loadWeatherForecastOn5Days(String city, String units);
}
