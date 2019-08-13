package com.example.weatherapp.utils;

import com.example.weatherapp.models.SelectedCities;

public class ConfSingleton {

    private SelectedCities selectedCities;

    private static final ConfSingleton ourInstance = new ConfSingleton();

    public SelectedCities getSelectedCities() {
        return selectedCities;
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
