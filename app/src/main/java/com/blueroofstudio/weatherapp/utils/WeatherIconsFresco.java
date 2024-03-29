package com.blueroofstudio.weatherapp.utils;

import android.net.Uri;

public class WeatherIconsFresco {

    private static final String url = "https://openweathermap.org/img/wn/%s@2x.png";

    public static Uri getWeatherIcon(String weatherIcon) {
        return Uri.parse(String.format(url, weatherIcon));
    }
}
