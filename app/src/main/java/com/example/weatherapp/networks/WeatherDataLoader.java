package com.example.weatherapp.networks;

import com.example.weatherapp.interfaces.WeatherDataSource;
import com.example.weatherapp.models.pojo.CurrentWeatherData;
import com.example.weatherapp.models.WeatherItem;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherDataLoader implements WeatherDataSource {

    private static final String OPEN_WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s";
    private static final String API_KEY = "fe3618c262f07832f51e85565bc3f81e";
    private static final String NEW_LINE = "\n";

    @Override
    public WeatherItem loadCurrentWeatherData(String city, String units) {
        try {
            final URL uri = new URL(String.format(OPEN_WEATHER_API, city, API_KEY, units));
            try {
                HttpsURLConnection urlConnection;
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder result = new StringBuilder(1024);
                String tempData;
                while ((tempData = in.readLine()) != null) {
                    result.append(tempData).append(NEW_LINE);
                }
                in.close();
                Gson gson = new Gson();
                CurrentWeatherData currentWeather = gson.fromJson(result.toString(), CurrentWeatherData.class);
                if (currentWeather != null) {
                    return new WeatherItem(currentWeather);
                }
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
}
