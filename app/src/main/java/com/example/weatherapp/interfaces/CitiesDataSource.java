package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.restEntities.City;

import java.util.List;

public interface CitiesDataSource {
    List<City> findCityByName(String cityName);
}
