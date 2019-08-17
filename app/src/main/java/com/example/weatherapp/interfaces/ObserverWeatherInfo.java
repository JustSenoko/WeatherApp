package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.WeatherItem;

import java.util.List;

public interface ObserverWeatherInfo {
    void updateCurrentWeatherViews(WeatherItem weather);
    void updateWeatherForecastViews(List<WeatherItem> forecast);
}
