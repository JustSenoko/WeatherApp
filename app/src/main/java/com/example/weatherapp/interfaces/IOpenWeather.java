package com.example.weatherapp.interfaces;

import com.example.weatherapp.models.restEntities.CurrentWeatherData;
import com.example.weatherapp.models.restEntities.WeatherForecastList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<CurrentWeatherData> loadCurrentWeatherByCityName(@Query("appid") String APIkey,
                                                          @Query("q") String city,
                                                          @Query("units") String units);

    @GET("data/2.5/weather")
    Call<CurrentWeatherData> loadCurrentWeatherByCityId(@Query("appid") String APIkey,
                                                        @Query("id") int id,
                                                        @Query("units") String units,
                                                        @Query("lang") String language);

    @GET("data/2.5/forecast")
    Call<WeatherForecastList> loadWeatherForecastByCityId(@Query("appid") String APIkey,
                                                          @Query("id") int id,
                                                          @Query("units") String units);
}
