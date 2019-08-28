package com.blueroofstudio.weatherapp.models;

import com.blueroofstudio.weatherapp.R;

public class Units {

    public static String getTemperatureUnit(boolean useImperialUnits) {
        return (useImperialUnits ? "°F" : "°C");
    }

    public static int getWindUnit(boolean useImperialUnits) {
        return (useImperialUnits ? R.string.miles_hour : R.string.meter_sec);
    }

    public static String getUnitsName(boolean useImperialUnits) {
        return (useImperialUnits ? "imperial" : "metric");
    }
}
