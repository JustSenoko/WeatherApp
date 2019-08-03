package com.example.weatherapp.models;

import com.example.weatherapp.models.pojo.CurrentWeatherData;
import com.example.weatherapp.models.pojo.WeatherForecast;

import java.util.Date;

public class WeatherItem {

    private final Date date;
    private final int temperature;
    private final int pressure;
    private final float wind;
    private final String weather;

    public WeatherItem(Date date, int temperature, int pressure, float wind, String weather) {
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.wind = wind;
        this.weather = weather;
    }

    public WeatherItem(CurrentWeatherData currentWeather) {
        date = currentWeather.getDate();
        temperature = (int) currentWeather.getMain().getTemp();
        pressure = currentWeather.getMain().getPressure();
        wind = (int) currentWeather.getWind().getSpeed();
        weather = currentWeather.getWeather().getDescription();
    }

    public WeatherItem(WeatherForecast weatherForecast) {
        date = weatherForecast.getDate();
        temperature = (int) weatherForecast.getMain().getTemp();
        pressure = weatherForecast.getMain().getPressure();
        wind = weatherForecast.getWind().getSpeed();
        weather = weatherForecast.getWeather().getDescription();
    }

    public Date getDate() {
        return date;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public float getWind() {
        return wind;
    }

    public String getWeather() {
        return weather;
    }
}
