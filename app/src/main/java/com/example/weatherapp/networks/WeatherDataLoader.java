package com.example.weatherapp.networks;

import android.location.Location;

import androidx.annotation.NonNull;

import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.restEntities.City;
import com.example.weatherapp.models.restEntities.CurrentWeatherData;
import com.example.weatherapp.models.restEntities.WeatherForecast;
import com.example.weatherapp.models.restEntities.WeatherForecastList;
import com.example.weatherapp.utils.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataLoader {

    private static final String API_KEY = "fe3618c262f07832f51e85565bc3f81e";

    public static void findCityByName(final Publisher publisher, String cityName) {

        OpenWeatherRepo.getInstance().getAPI().loadCurrentWeatherByCityName(API_KEY, cityName, "metric")
                .enqueue(new Callback<CurrentWeatherData>() {
                    @Override
                    public void onResponse(@NonNull Call<CurrentWeatherData> call,
                                           @NonNull Response<CurrentWeatherData> response) {
                        City city = null;
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherItem weather = new WeatherItem(response.body());
                            city = weather.getCity();
                        }
                        publisher.notifyCityFoundResult(city);
                    }

                    @Override
                    public void onFailure(@NonNull Call<CurrentWeatherData> call,
                                          @NonNull Throwable t) {
                        publisher.notifyCityFoundResult(null);
                    }
                });

    }

    private static void loadCurrentWeatherData(final Publisher publisher, Call<CurrentWeatherData> dataCall) {
        dataCall.enqueue(new Callback<CurrentWeatherData>() {
                    @Override
                    public void onResponse(@NonNull Call<CurrentWeatherData> call,
                                           @NonNull Response<CurrentWeatherData> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            publisher.notifyUpdateWeatherInfo(new WeatherItem(response.body()));
                        } else {
                            publisher.notifyUpdateWeatherInfo(null);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CurrentWeatherData> call,
                                          @NonNull Throwable t) {
                        publisher.notifyUpdateWeatherInfo(null);
                    }
                });
    }

    public static void loadCurrentWeatherDataByCityId(final Publisher publisher, int cityId, String units) {
        String language = Locale.getDefault().getLanguage();
        Call<CurrentWeatherData> dataCall = OpenWeatherRepo.getInstance().getAPI().loadCurrentWeatherByCityId(API_KEY, cityId, units, language);
        loadCurrentWeatherData(publisher, dataCall);
    }

    public static void loadCurrentWeatherDataByLocation(final Publisher publisher, Location location, String units) {
        String language = Locale.getDefault().getLanguage();
        Call<CurrentWeatherData> dataCall = OpenWeatherRepo.getInstance().getAPI().loadCurrentWeatherByLocation(API_KEY, units,
                location.getLatitude(), location.getLongitude(), language);
        loadCurrentWeatherData(publisher, dataCall);
    }

    private static void loadWeatherForecastOn5Days(final Publisher publisher, Call<WeatherForecastList> dataCall) {
        dataCall.enqueue(new Callback<WeatherForecastList>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherForecastList> call,
                                           @NonNull Response<WeatherForecastList> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            List<WeatherItem> weatherItemList = new ArrayList<>();
                            WeatherForecastList forecast = response.body();
                            City cityForecast = forecast.city;
                            for (WeatherForecast weather : forecast.list) {
                                weatherItemList.add(new WeatherItem(cityForecast, weather));
                            }
                            publisher.notifyUpdateWeatherForecastInfo(weatherItemList);
                        } else {
                            publisher.notifyUpdateWeatherForecastInfo(new ArrayList<WeatherItem>());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherForecastList> call,
                                          @NonNull Throwable t) {
                        publisher.notifyUpdateWeatherForecastInfo(new ArrayList<WeatherItem>());
                    }
                });
    }

    public static void loadWeatherForecastOn5DaysByCityId(final Publisher publisher, int cityId, String units) {
        Call<WeatherForecastList> dataCall = OpenWeatherRepo.getInstance().getAPI().loadWeatherForecastByCityId(API_KEY, cityId, units);
        loadWeatherForecastOn5Days(publisher, dataCall);
    }

    public static void loadWeatherForecastOn5DaysByLocation(final Publisher publisher, Location location, String units) {
        Call<WeatherForecastList> dataCall = OpenWeatherRepo.getInstance().getAPI().loadWeatherForecastByLocation(API_KEY,
                location.getLatitude(), location.getLongitude(), units);
        loadWeatherForecastOn5Days(publisher, dataCall);
    }
}
