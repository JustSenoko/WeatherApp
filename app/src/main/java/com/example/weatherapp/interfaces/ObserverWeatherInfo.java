package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.WeatherItem;

import java.util.List;

public interface ObserverWeatherInfo {
    void updateWeatherInfo(WeatherItem weather);
    void updateWeatherForecastInfo(List<WeatherItem> forecast);
}
