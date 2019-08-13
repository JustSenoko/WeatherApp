package com.example.weatherapp.models;

import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.models.pojo.CurrentWeatherData;
import com.example.weatherapp.models.pojo.WeatherForecast;

import java.io.Serializable;
import java.util.Date;

public class WeatherItem implements Serializable {

    private final Date date;
    private final City city;
    private final int temperature;
    private final int pressure;
    private final int humidity;
    private final float wind;
    private final String weather;

    public WeatherItem(Date date, City city, int temperature, int pressure, int humidity, float wind, String weather) {
        this.date = date;
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.weather = weather;
    }

    public WeatherItem(CurrentWeatherData currentWeather) {
        date = currentWeather.getDate();
        city = new City(currentWeather.getName(), currentWeather.getId());
        temperature = (int) currentWeather.getMain().getTemp();
        pressure = (int) currentWeather.getMain().getPressure();
        humidity = (int) currentWeather.getMain().getHumidity();
        wind = (int) currentWeather.getWind().getSpeed();
        weather = currentWeather.getWeather().getDescription();
    }

    public WeatherItem(City city, WeatherForecast weatherForecast) {
        this.city = city;
        date = weatherForecast.getDate();
        temperature = (int) weatherForecast.getMain().getTemp();
        pressure = (int) weatherForecast.getMain().getPressure();
        humidity = (int) weatherForecast.getMain().getHumidity();
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

    public City getCity() {
        return city;
    }

    public int getHumidity() {
        return humidity;
    }
}
