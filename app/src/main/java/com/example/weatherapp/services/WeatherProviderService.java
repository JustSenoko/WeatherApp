package com.example.weatherapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.interfaces.WeatherDataSource;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.networks.WeatherDataLoader_OpenWeather;

import java.util.ArrayList;
import java.util.List;

public class WeatherProviderService extends IntentService {

    private static final String ACTION_FIND_CITY_BY_NAME = "com.example.weatherapp.action.FIND_CITY_BY_NAME";
    private static final String ACTION_LOAD_CURRENT_WEATHER = "com.example.weatherapp.action.LOAD_CURRENT_WEATHER";
    private static final String ACTION_LOAD_FORECAST_5_DAYS = "com.example.weatherapp.action.LOAD_FORECAST_5_DAYS";

    private static final String CITY = "com.example.weatherapp.extra.CURRENT_CITY";
    private static final String UNITS = "com.example.weatherapp.extra.UNITS";

    private final WeatherDataSource weatherDataSource = new WeatherDataLoader_OpenWeather();
    //private final WeatherDataSource weatherDataSource = new WeatherDataLoader_FakeData();

    public WeatherProviderService() {
        super("WeatherProviderService");
    }

    public static void startFindCityByName(Context context, String cityName) {
        Intent intent = new Intent(context, WeatherProviderService.class);
        intent.setAction(ACTION_FIND_CITY_BY_NAME);
        intent.putExtra(CITY, cityName);
        context.startService(intent);
    }

    public static void startCurrentWeatherLoad(Context context, String cityName, String units) {
        Intent intent = new Intent(context, WeatherProviderService.class);
        intent.setAction(ACTION_LOAD_CURRENT_WEATHER);
        intent.putExtra(CITY, cityName);
        intent.putExtra(UNITS, units);
        context.startService(intent);
    }

    public static void startForecastLoad(Context context, String cityName, String units) {
        Intent intent = new Intent(context, WeatherProviderService.class);
        intent.setAction(ACTION_LOAD_FORECAST_5_DAYS);
        intent.putExtra(CITY, cityName);
        intent.putExtra(UNITS, units);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            final String cityName = intent.getStringExtra(CITY);
            if (ACTION_LOAD_CURRENT_WEATHER.equals(action)) {
                final String units = intent.getStringExtra(UNITS);
                handleLoadCurrentWeather(cityName, units);
            } else if (ACTION_FIND_CITY_BY_NAME.equals(action)) {
                handleFindCityByName(cityName);
            } else if (ACTION_LOAD_FORECAST_5_DAYS.equals(action)) {
                final String units = intent.getStringExtra(UNITS);
                handleLoadForecast5Days(cityName, units);
            }
        }
    }

   private void handleLoadCurrentWeather(String city, String units) {
        WeatherItem currentWeather = weatherDataSource.loadCurrentWeatherData(city, units);
        Intent broadcastIntent = new Intent(MainActivity.CURRENT_WEATHER_BROADCAST_INTENT);
        broadcastIntent.putExtra("WeatherItem", currentWeather);
        sendBroadcast(broadcastIntent);
    }

    private void handleFindCityByName(String city) {
        WeatherItem weather = weatherDataSource.loadCurrentWeatherData(city, Units.getUnitsName(false));
        Intent broadcastIntent = new Intent(MainActivity.FIND_CITY_RESULT_BROADCAST_INTENT);
        City foundCity = (weather == null ? null : weather.getCity());
        broadcastIntent.putExtra("City", foundCity);
        sendBroadcast(broadcastIntent);
    }

    private void handleLoadForecast5Days(String city, String units) {
        List<WeatherItem> forecast = weatherDataSource.loadWeatherForecastOn5Days(city, units);
        Intent broadcastIntent = new Intent(MainActivity.WEATHER_FORECAST_BROADCAST_INTENT);
        broadcastIntent.putParcelableArrayListExtra("WeatherItems", (ArrayList<WeatherItem>) forecast);
        sendBroadcast(broadcastIntent);
    }
}
