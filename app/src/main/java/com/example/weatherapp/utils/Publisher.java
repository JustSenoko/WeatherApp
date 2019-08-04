package com.example.weatherapp.utils;

import com.example.weatherapp.interfaces.ObserverCityList;
import com.example.weatherapp.models.pojo.City;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<ObserverCityList> observerCityLists;   // Все обозреватели

    public Publisher() {
        observerCityLists = new ArrayList<>();
    }

    // Подписать
    public void subscribeCityList(ObserverCityList observerCityList) {
        observerCityLists.add(observerCityList);
    }

    // Разослать событие
    public void notifyDeleteCity(City city) {
        for (ObserverCityList observerCityList : observerCityLists) {
            observerCityList.deleteSelectedCity(city);
        }
    }
}
