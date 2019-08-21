package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class UserPreferences {

    private static final String NAME_SHARED_PREFERENCES = "Weather_App";

    private static final String IS_DARK_THEME = "dark_theme";
    private static final String CURRENT_CITY_ID = "current_city_id";
    private static final String SHOW_PRESSURE = "show_pressure";
    private static final String SHOW_HUMIDITY = "show_humidity";
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
        return sharedPreferences.getInt(CURRENT_CITY_ID, 0);
    }

    public void setCurrentCityId(int currentCityId) {
        sharedPreferences.edit().putInt(CURRENT_CITY_ID, currentCityId).apply();
    }

    public boolean isShowPressure() {
        return sharedPreferences.getBoolean(SHOW_PRESSURE, true);
    }

    public void setShowPressure(boolean showPressure) {
        sharedPreferences.edit().putBoolean(SHOW_PRESSURE, showPressure).apply();
    }

    public boolean isShowHumidity() {
        return sharedPreferences.getBoolean(SHOW_HUMIDITY, true);
    }

    public void setShowHumidity(boolean showHumidity) {
        sharedPreferences.edit().putBoolean(SHOW_HUMIDITY, showHumidity).apply();
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
}
