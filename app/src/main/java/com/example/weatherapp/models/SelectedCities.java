package com.example.weatherapp.models;

import com.example.weatherapp.models.pojo.City;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SelectedCities implements Serializable {

    private List<City> selectedCitiesList = new ArrayList<>();
    private City currentCity = null;

    public SelectedCities() {
//        this("", 0);
    }

    public SelectedCities(String selectedCitiesJSON, int currentCityId) {

        if (selectedCitiesJSON.isEmpty()) {
            return;
        }
        Gson gson = new Gson();
        City[] selectedCities = gson.fromJson(selectedCitiesJSON, City[].class);
        if (selectedCities == null || selectedCities.length == 0) {
            return;
        }
        selectedCitiesList = new ArrayList<>(Arrays.asList(selectedCities));
        if (currentCityId == 0) {
            currentCity = selectedCitiesList.get(0);
        } else {
            for (City city : selectedCities) {
                if (city.getId() == currentCityId) {
                    currentCity = city;
                    break;
                }
            }
        }
    }

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
        if (this.currentCity.getName().equals(city.getName()) && selectedCitiesList.size() > 0) {
            this.currentCity = selectedCitiesList.get(0);
        }
    }

    public void setCurrentCity(City city) {
        this.currentCity = city;
    }

    public City getCurrentCity() {
        return currentCity;
    }
}
