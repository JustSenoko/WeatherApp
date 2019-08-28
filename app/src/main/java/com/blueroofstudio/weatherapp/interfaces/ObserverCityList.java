package com.blueroofstudio.weatherapp.interfaces;

import com.blueroofstudio.weatherapp.models.restEntities.City;

public interface ObserverCityList {
    void deleteSelectedCity(City city);
    void findCityByNameResult(City city);
}
