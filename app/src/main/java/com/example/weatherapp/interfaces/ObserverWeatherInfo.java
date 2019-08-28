package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.WeatherItem;

import java.util.List;

public interface ObserverWeatherInfo {
    void onReceiveCurrentWeatherInfo(WeatherItem weather);
    void onReceiveWeatherForecast(List<WeatherItem> forecast);
}
