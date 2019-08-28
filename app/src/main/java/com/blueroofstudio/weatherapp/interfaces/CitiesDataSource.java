package com.blueroofstudio.weatherapp.interfaces;

import com.blueroofstudio.weatherapp.models.restEntities.City;

import java.util.List;

public interface CitiesDataSource {
    List<City> findCityByName(String cityName);
}
