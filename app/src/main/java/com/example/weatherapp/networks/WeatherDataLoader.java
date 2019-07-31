package com.example.weatherapp.networks;

import com.example.weatherapp.interfaces.DataSource;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.CurrentWeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherDataLoader implements DataSource {

    private static final String OPEN_WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?q=%s,RU&appid=%s&units=%s";
    private static final String API_KEY = "fe3618c262f07832f51e85565bc3f81e";
    private static final String NEW_LINE = "\n";

    public static CurrentWeatherRequest loadCurrentWeatherData(String city, String units) {
        try {
            final URL uri = new URL(String.format(OPEN_WEATHER_API, city, API_KEY, units));
            try {
                HttpsURLConnection urlConnection;
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                final String result = in.lines().collect(Collectors.joining(NEW_LINE));
                Gson gson = new Gson();
                return gson.fromJson(result, CurrentWeatherRequest.class);
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean findCity(String cityName) {
        return false;
    }

    @Override
    public boolean loadCityWeather(String cityName) {
        return false;
    }

    @Override
    public WeatherItem getWeather(String city, Date date) {
        return null;
    }
}
