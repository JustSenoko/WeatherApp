package com.blueroofstudio.weatherapp.interfaces;

import com.blueroofstudio.weatherapp.models.WeatherItem;

import java.util.List;

public interface ObserverWeatherInfo {
    void onReceiveCurrentWeatherInfo(WeatherItem weather);
    void onReceiveWeatherForecast(List<WeatherItem> forecast);
}
