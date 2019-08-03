package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.POJO.City;

public interface CitiesDataSource {
    City findCityByName(String cityName);
}
