package com.example.weatherapp.utils;

import com.example.weatherapp.interfaces.ObserverCityList;
import com.example.weatherapp.interfaces.ObserverWeatherInfo;
import com.example.weatherapp.models.pojo.City;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<ObserverCityList> observerCityLists;   // Все обозреватели
    private List<ObserverWeatherInfo> observerWeatherInfo;   // Все обозреватели

    public Publisher() {
        observerCityLists = new ArrayList<>();
        observerWeatherInfo = new ArrayList<>();
    }

    // Подписать
    public void subscribeCityList(ObserverCityList observerCityList) {
        observerCityLists.add(observerCityList);
    }

    public void subscribeWeatherInfo(ObserverWeatherInfo observerWeatherInfo) {
        this.observerWeatherInfo.add(observerWeatherInfo);
    }

    // Разослать событие
    public void notifyDeleteCity(City city) {
        for (ObserverCityList observerCityList : observerCityLists) {
            observerCityList.deleteSelectedCity(city);
        }
    }

    public void notifyUpdateWeatherInfo() {
        for (ObserverWeatherInfo observer : observerWeatherInfo) {
            observer.updateWeatherInfo();
        }
    }



}
