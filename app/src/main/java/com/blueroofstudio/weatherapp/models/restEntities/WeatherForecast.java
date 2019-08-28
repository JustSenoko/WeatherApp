package com.blueroofstudio.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SuppressWarnings({"unused"})
public class WeatherForecast {
    @SerializedName("dt") public int dt;
    @SerializedName("dt_txt") public String dateTxt;
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
    @SerializedName("wind") public Wind wind;

    public Weather getWeather() {
        return weather.length == 0 ? null : weather[0];
    }

    public Date getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return formatter.parse(dateTxt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}