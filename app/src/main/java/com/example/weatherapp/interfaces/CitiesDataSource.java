package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.pojo.City;

public interface CitiesDataSource {
    City findCityByName(String cityName);
}
