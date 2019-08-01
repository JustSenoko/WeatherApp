package com.example.weatherapp.models;

import com.example.weatherapp.interfaces.DataSource;
import com.example.weatherapp.utils.MainPresenter;

import java.util.Date;

public class City {
    private final String name;
    private WeatherItem currentWeather;
    private CurrentWeatherRequest weatherRequest;

    public City(String name) {
        this.name = name;
        DataSource dataSource = MainPresenter.getInstance().getDataSource();
        loadCurrentWeather(name, dataSource);
    }

    public City(String name, DataSource dataSource) {
        this.name = name;
        loadCurrentWeather(name, dataSource);

    }

    private void loadCurrentWeather(String cityName, DataSource dataSource) {
        if (dataSource.loadCityWeather(name)) {
            this.currentWeather = dataSource.getWeather(name, new Date());
        } else {
            currentWeather = new WeatherItem(new Date(), 17, 736, 4, 16, "sunny");
        }
    }

    public City(String name, CurrentWeatherRequest weatherRequest) {
        this.name = name;
        this.weatherRequest = weatherRequest;
    }

    public String getName() {
        return name;
    }

    public int getCurrentTemperature() {
        return currentWeather.getTemperature();
    }

    public String getCurrentWeather() {
        return currentWeather.getWeather();
    }
}
