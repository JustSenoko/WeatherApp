package com.example.weatherapp.networks;

import com.example.weatherapp.interfaces.WeatherDataSource;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.restEntities.City;
import com.example.weatherapp.models.restEntities.CurrentWeatherData;
import com.example.weatherapp.models.restEntities.WeatherForecast;
import com.example.weatherapp.models.restEntities.WeatherForecastList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherDataLoader_OpenWeather implements WeatherDataSource {

    private static final String CURRENT_WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s";
    private static final String FORECAST_API = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=%s";

    private static final String API_KEY = "fe3618c262f07832f51e85565bc3f81e";
    private static final String NEW_LINE = "\n";

    @Override
    public WeatherItem loadCurrentWeatherData(String city, String units) {
        try {
            URL url = new URL(String.format(CURRENT_WEATHER_API, city, API_KEY, units));
            String result = URLResult(url);

            Gson gson = new Gson();
            CurrentWeatherData currentWeather = gson.fromJson(result, CurrentWeatherData.class);
            if (currentWeather != null) {
                return new WeatherItem(currentWeather);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<WeatherItem> loadWeatherForecastOn5Days(String city, String units) {
        List<WeatherItem> weatherItemList = new ArrayList<>();
        try {
            URL url = new URL(String.format(FORECAST_API, city, API_KEY, units));
            String result = URLResult(url);

            Gson gson = new Gson();
            WeatherForecastList forecast = gson.fromJson(result, WeatherForecastList.class);
            if (forecast != null) {
                City cityForecast = forecast.city;
                for (WeatherForecast weather : forecast.list) {
                    weatherItemList.add(new WeatherItem(cityForecast, weather));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return weatherItemList;
    }

    private String URLResult(URL url) {
        StringBuilder result = new StringBuilder(1024);
        try {
            HttpsURLConnection urlConnection;
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String tempData;
            while ((tempData = in.readLine()) != null) {
                result.append(tempData).append(NEW_LINE);
            }
            in.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
