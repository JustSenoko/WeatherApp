package com.example.weatherapp.utils;

import com.example.weatherapp.interfaces.CitiesDataSource;
import com.example.weatherapp.models.SelectedCities;

public class ConfSingleton {
    private SelectedCities selectedCities;
    private CitiesDataSource citiesData;

    private static final ConfSingleton ourInstance = new ConfSingleton();

    public SelectedCities getSelectedCities() {
        return selectedCities;
    }

    public CitiesDataSource getCitiesData() {
        return citiesData;
    }

    public void setCitiesData(CitiesDataSource citiesData) {
        this.citiesData = citiesData;
    }

    public void setSelectedCities(SelectedCities selectedCities) {
        this.selectedCities = selectedCities;
    }

    public static ConfSingleton getInstance() {
        return ourInstance;
    }

    private ConfSingleton() {
    }
}
