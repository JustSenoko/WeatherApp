package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.pojo.City;

public interface ObserverCityList {
    void deleteSelectedCity(City city);
    void findCityByNameResult(City city);
}
