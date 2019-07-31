package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class UserPreferences {

    private static final String NAME_SHARED_PREFERENCES = "Weather_App";

    private static final String IS_DARK_THEME = "dark_theme";
    private static final String CURRENT_CITY = "city";
    private static final String SHOW_PRESSURE = "show_pressure";
    private static final String SHOW_WIND = "show_wind";
    private static final String SHOW_FEELS_LIKE = "show_feels_like";

    private SharedPreferences sharedPreferences;

    public UserPreferences(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE);
    }

    public boolean isDarkTheme() {
        return sharedPreferences.getBoolean(IS_DARK_THEME, false);
    }

    public void setDarkTheme(boolean isDarkTheme) {
        sharedPreferences.edit().putBoolean(IS_DARK_THEME, isDarkTheme).apply();
    }

    public String getCurrentCity() {
        return sharedPreferences.getString(CURRENT_CITY, "Moscow");
    }

    public void setCurrentCity(String currentCity) {
        sharedPreferences.edit().putString(CURRENT_CITY, currentCity).apply();
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

    public boolean isShowFeelsLike() {
        return sharedPreferences.getBoolean(SHOW_FEELS_LIKE, true);
    }

    public void setShowFeelsLike(boolean showFeelsLike) {
        sharedPreferences.edit().putBoolean(SHOW_FEELS_LIKE, showFeelsLike).apply();
    }
}
