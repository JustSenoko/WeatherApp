package com.example.weatherapp.service;

import java.util.ArrayList;
import java.util.List;

public final class MainPresenter {

    //Внутреннее поле, будет хранить единственный экземпляр
    private static MainPresenter instance = null;

    // Поле для синхронизации
    private static final Object syncObj = new Object();

    private String city;
    private boolean showPressure;
    private boolean showWind;
    private boolean showFeelsLike;
    private TemperatureUnits tUnit;

    private List<String> cities = new ArrayList<>();
    private int currentCityIndex;

    // Конструктор (вызывать извне его нельзя, поэтому он приватный)
    private MainPresenter(){
        if (cities.size() == 0) {
            cities.add("Moscow");
            cities.add("London");
            cities.add("Saint Petersburg");
        }
        city = "Moscow";
        currentCityIndex = 0;
        showPressure = true;
        showWind = true;
        showFeelsLike = true;
        tUnit = TemperatureUnits.C;
    }

    public boolean isShowPressure() {
        return showPressure;
    }

    public boolean isShowWind() {
        return showWind;
    }

    public boolean isShowFeelsLike() {
        return showFeelsLike;
    }

    public String getCity() {
        return city;
    }

    public TemperatureUnits gettUnit() {
        return tUnit;
    }

    public void setShowPressure(boolean showPressure) {
        this.showPressure = showPressure;
    }

    public void setShowWind(boolean showWind) {
        this.showWind = showWind;
    }

    public void setShowFeelsLike(boolean showFeelsLike) {
        this.showFeelsLike = showFeelsLike;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void settUnit(TemperatureUnits tUnit) {
        this.tUnit = tUnit;
    }

    // Метод, который возвращает экземпляр объекта.
    // Если объекта нет, то создаем его.
    public static MainPresenter getInstance(){
        // Здесь реализована «ленивая» инициализация объекта,
        // то есть, пока объект не нужен, не создаем его.
        synchronized (syncObj) {
            if (instance == null) {
                instance = new MainPresenter();
            }
            return instance;
        }
    }

}
