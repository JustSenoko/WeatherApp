package com.example.weatherapp.utils;

import com.example.weatherapp.interfaces.ObserverCityList;
import com.example.weatherapp.interfaces.ObserverWeatherInfo;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.restEntities.City;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private final List<ObserverCityList> observerCityLists;   // Все обозреватели
    private final List<ObserverWeatherInfo> observerWeatherInfoList;   // Все обозреватели

    public Publisher() {
        observerCityLists = new ArrayList<>();
        observerWeatherInfoList = new ArrayList<>();
    }

    // Подписать
    public void subscribeCityList(ObserverCityList observerCityList) {
        observerCityLists.add(observerCityList);
    }
    public void subscribeWeatherInfo(ObserverWeatherInfo observer) {
        observerWeatherInfoList.add(observer);
    }

    // Разослать событие
    public void notifyDeleteCity(City city) {
        for (ObserverCityList observerCityList : observerCityLists) {
            observerCityList.deleteSelectedCity(city);
        }
    }

    public void notifyCityFoundResult(City city) {
        for (ObserverCityList observer : observerCityLists) {
            observer.findCityByNameResult(city);
        }
    }

    // Разослать событие
    public void notifyUpdateWeatherInfo(WeatherItem weatherItem) {
        for (ObserverWeatherInfo observer : observerWeatherInfoList) {
            observer.updateCurrentWeatherViews(weatherItem);
        }
    }

    public void notifyUpdateWeatherForecastInfo(List<WeatherItem> forecast) {
        for (ObserverWeatherInfo observer : observerWeatherInfoList) {
            observer.updateWeatherForecastViews(forecast);
        }
    }
}
