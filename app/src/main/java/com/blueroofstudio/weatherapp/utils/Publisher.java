package com.blueroofstudio.weatherapp.utils;

import com.blueroofstudio.weatherapp.interfaces.ObserverCityList;
import com.blueroofstudio.weatherapp.interfaces.ObserverWeatherInfo;
import com.blueroofstudio.weatherapp.models.WeatherItem;
import com.blueroofstudio.weatherapp.models.restEntities.City;

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

    public void unsubscribeCityList(ObserverCityList observer) {
        observerCityLists.remove(observer);
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
            observer.onReceiveCurrentWeatherInfo(weatherItem);
        }
    }

    public void notifyUpdateWeatherForecastInfo(List<WeatherItem> forecast) {
        for (ObserverWeatherInfo observer : observerWeatherInfoList) {
            observer.onReceiveWeatherForecast(forecast);
        }
    }
}