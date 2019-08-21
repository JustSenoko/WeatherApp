package com.example.weatherapp.utils;

import android.net.Uri;

public class WeatherIconsFresco {

    private static final String url = "http://openweathermap.org/img/wn/%s@2x.png";

    public static Uri getWeatherIcon(String weatherIcon) {
        return Uri.parse(String.format(url, weatherIcon));
    }
}
