package com.example.weatherapp.models;

import com.example.weatherapp.models.pojo.City;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SelectedCities {

    private List<City> selectedCitiesList = new ArrayList<>();
    private City currentCity = null;

    public SelectedCities(String selectedCitiesJSON, int currentCityId) {

        if (selectedCitiesJSON.isEmpty()) {
            return;
        }
        Gson gson = new Gson();
        City[] selectedCities = gson.fromJson(selectedCitiesJSON, City[].class);
        if (selectedCities == null || selectedCities.length == 0) {
            return;
        }
        selectedCitiesList = Arrays.asList(selectedCities);
        for (City city : selectedCities) {
            if (city.getId() == currentCityId) {
                currentCity = city;
                break;
            }
        }
    }

    private SelectedCities() {
        selectedCitiesList = new ArrayList<>();
        currentCity = null;
    }

    public List<City> getSelectedCitiesList() {
        return selectedCitiesList;
    }

    public void addCity(City city) {
        if (selectedCitiesList.contains(city)) {
            return;
        }
        selectedCitiesList.add(city);
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
