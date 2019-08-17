package com.example.weatherapp.networks;

import androidx.annotation.NonNull;

import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.restEntities.City;
import com.example.weatherapp.models.restEntities.CurrentWeatherData;
import com.example.weatherapp.models.restEntities.WeatherForecast;
import com.example.weatherapp.models.restEntities.WeatherForecastList;
import com.example.weatherapp.utils.Publisher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataLoader {

    private static final String API_KEY = "fe3618c262f07832f51e85565bc3f81e";

    public static void findCityByName(final Publisher publisher, String cityName) {

        OpenWeatherRepo.getInstance().getAPI().loadCurrentWeatherByCityName(cityName, API_KEY, "metric")
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

    public static void loadCurrentWeatherDataByCityId(final Publisher publisher, int cityId, String units) {
        OpenWeatherRepo.getInstance().getAPI().loadCurrentWeatherByCityId(cityId, API_KEY, units)
                .enqueue(new Callback<CurrentWeatherData>() {
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

    public static void loadWeatherForecastOn5Days(final Publisher publisher, int cityId, String units) {
        OpenWeatherRepo.getInstance().getAPI().loadWeatherForecastByCityId(cityId, API_KEY, units)
                .enqueue(new Callback<WeatherForecastList>() {
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
}
