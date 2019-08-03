package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.weatherapp.models.SelectedCities;
import com.google.gson.Gson;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class UserPreferences {

    private static final String NAME_SHARED_PREFERENCES = "Weather_App";

    private static final String IS_DARK_THEME = "dark_theme";
    private static final String CURRENT_CITY_ID = "city_id";
    private static final String SELECTED_CITIES_JSON = "selected_cities_json";
    private static final String SHOW_PRESSURE = "show_pressure";
    private static final String SHOW_WIND = "show_wind";
    private static final String USE_IMPERIAL_UNITS = "use_imperial_units";

    private final SharedPreferences sharedPreferences;

    public UserPreferences(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE);
    }

    public boolean isDarkTheme() {
        return sharedPreferences.getBoolean(IS_DARK_THEME, false);
    }

    public void setDarkTheme(boolean isDarkTheme) {
        sharedPreferences.edit().putBoolean(IS_DARK_THEME, isDarkTheme).apply();
    }

    public int getCurrentCityId() {
        return sharedPreferences.getInt(CURRENT_CITY_ID, 524901); // Moscow
    }

    public void setCurrentCityId(int id) {
        sharedPreferences.edit().putInt(CURRENT_CITY_ID, id).apply();
    }

    public boolean isShowPressure() {
        return sharedPreferences.getBoolean(SHOW_PRESSURE, true);
    }

    public void setShowPressure(boolean showPressure) {
        sharedPreferences.edit().putBoolean(SHOW_PRESSURE, showPressure).apply();
    }

    public boolean isShowWind() {
        return sharedPreferences.getBoolean(SHOW_WIND, true);
    }

    public void setShowWind(boolean showWind) {
        sharedPreferences.edit().putBoolean(SHOW_WIND, showWind).apply();
    }

    public boolean useImperialUnits() {
        return sharedPreferences.getBoolean(USE_IMPERIAL_UNITS, false);
    }

    public void setUseImperialUnits(boolean useImperialUnits) {
        sharedPreferences.edit().putBoolean(USE_IMPERIAL_UNITS, useImperialUnits).apply();
    }

    public SelectedCities getSelectedCities() {
        return new SelectedCities(Objects.requireNonNull(sharedPreferences.getString(SELECTED_CITIES_JSON, "")), getCurrentCityId());
    }

    public void setSelectedCitiesJson(SelectedCities selectedCities) {
        Gson gson = new Gson();
        String selectedCitiesJSON = gson.toJson(selectedCities.getSelectedCitiesList());
        sharedPreferences.edit().putString(SELECTED_CITIES_JSON, selectedCitiesJSON).apply();
    }
}
