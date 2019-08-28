package com.blueroofstudio.weatherapp.utils;

import android.content.Context;

import com.blueroofstudio.weatherapp.R;

public class WeatherIconsFont {
    public static String getWeatherIcon(Context context, int weatherId) {
        int type = weatherId / 100;

        if (weatherId == 800) {
            return context.getString(R.string.weather_icon_sunny);
        } else {
            switch (type) {
                case 2:
                    return context.getString(R.string.weather_icon_thunder);
                case 3:
                    return context.getString(R.string.weather_icon_drizzle);
                case 5:
                    return context.getString(R.string.weather_icon_rain);
                case 6:
                    return context.getString(R.string.weather_icon_snow);
                case 7:
                    return context.getString(R.string.weather_icon_mist);
                case 8:
                    return context.getString(R.string.weather_icon_clouds);
            }
        }
        return "";
    }
}
