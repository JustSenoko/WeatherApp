package com.example.weatherapp.models;

import com.example.weatherapp.models.POJO.City;

import java.util.ArrayList;
import java.util.List;

//Singleton
public final class SelectedCities {

    private List<City> selectedCities = new ArrayList<>();
    private City currentCity;

    //Внутреннее поле, будет хранить единственный экземпляр
    private static SelectedCities instance = null;
    // Поле для синхронизации
    private static final Object syncObj = new Object();

    private SelectedCities() {
    }

    public static SelectedCities getInstance(){
        synchronized (syncObj) {
            if (instance == null) {
                instance = new SelectedCities();
            }
            return instance;
        }
    }

    public List<City> getSelectedCities() {
        return selectedCities;
    }

    public void addCity(City city) {
        if (selectedCities.contains(city)) {
            return;
        }
        selectedCities.add(city);
    }

    public boolean cityIsInList(City city) {
        return selectedCities.contains(city);
    }

    public void deleteCity(City city) {
        selectedCities.remove(city);
        if (this.currentCity.getName().equals(city.getName()) && selectedCities.size() > 0) {
            this.currentCity = selectedCities.get(0);
        }
    }

    public void setCurrentCity(City city) {
        this.currentCity = city;
    }

    public City getCurrentCity() {
        return currentCity;
    }
}
