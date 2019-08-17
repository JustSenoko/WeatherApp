package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.weatherapp.models.SelectedCities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class UserPreferences {

    private static final String NAME_SHARED_PREFERENCES = "Weather_App";

    private static final String IS_DARK_THEME = "dark_theme";
    private static final String SHOW_PRESSURE = "show_pressure";
    private static final String SHOW_HUMIDITY = "show_humidity";
    private static final String SHOW_WIND = "show_wind";
    private static final String SHOW_SENSOR_TEMPERATURE = "show_sensor_temperature";
    private static final String SHOW_SENSOR_HUMIDITY = "show_sensor_humidity";
    private static final String USE_IMPERIAL_UNITS = "use_imperial_units";

    private final SharedPreferences sharedPreferences;
    private final String selectedCitiesFilePath;

    public UserPreferences(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE);

        String selectedCitiesFileName = "selected_cities.lect";
        selectedCitiesFilePath = activity.getFilesDir() + "/" + selectedCitiesFileName;
    }

    public boolean isDarkTheme() {
        return sharedPreferences.getBoolean(IS_DARK_THEME, false);
    }

    public void setDarkTheme(boolean isDarkTheme) {
        sharedPreferences.edit().putBoolean(IS_DARK_THEME, isDarkTheme).apply();
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

    public SelectedCities getSelectedCities() {
        SelectedCities selectedCities = (SelectedCities) readFromFile(selectedCitiesFilePath);
        if (selectedCities == null) {
            selectedCities = new SelectedCities();
        }
        return selectedCities;
    }

    public void setSelectedCities(SelectedCities selectedCities) {
        saveToFile(selectedCitiesFilePath, selectedCities);
    }

    private void saveToFile(String fileName, Object object) {
        File file;
        try {
            file = new File(fileName);

            FileOutputStream fileOutputStream;
            ObjectOutputStream objectOutputStream;

            if(!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            fileOutputStream  = new FileOutputStream(file, false);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(object);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private Object readFromFile(String fileName) {
        Object object = null;
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);

            object = objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();
        } catch(Exception exc) {
            exc.printStackTrace();
        }
        return object;
    }
}
