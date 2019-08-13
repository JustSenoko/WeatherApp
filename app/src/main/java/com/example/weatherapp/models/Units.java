package com.example.weatherapp.models;

import com.example.weatherapp.R;

public class Units {

    public static String getTemperatureUnit(boolean useImperialUnits) {
        return (useImperialUnits ? "F" : "C");
    }

    public static int getWindUnit(boolean useImperialUnits) {
        return (useImperialUnits ? R.string.miles_hour : R.string.meter_sec);
    }

    public static String getUnitsName(boolean useImperialUnits) {
        return (useImperialUnits ? "imperial" : "metric");
    }

    public static float convertCelsiusToFahrenheit(float celsius) {
        return (float) (celsius * 1.8 + 32);
    }
}
