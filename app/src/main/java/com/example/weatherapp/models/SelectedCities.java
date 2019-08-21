package com.example.weatherapp.models;

import com.example.weatherapp.models.restEntities.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class SelectedCities implements Serializable {

    private final List<City> selectedCitiesList = new ArrayList<>();
    private City currentCity = null;

    public List<City> getSelectedCitiesList() {
        return selectedCitiesList;
    }

    public void addCity(City city) {
        if (cityIsInList(city)) {
            return;
        }
        selectedCitiesList.add(city);
        if (currentCity == null) {
            currentCity = city;
        }
    }

    public boolean cityIsInList(City city) {
        return selectedCitiesList.contains(city);
    }

    public void deleteCity(City city) {
        selectedCitiesList.remove(city);
        if (this.currentCity.equals(city)) {
            if (selectedCitiesList.size() == 0) {
                currentCity = null;
            } else {
                currentCity = selectedCitiesList.get(0);
            }
        }
    }

    public void setCurrentCity(City city) {
        currentCity = city;
    }

    public City getCurrentCity() {
        return currentCity;
    }
}
