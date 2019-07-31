package com.example.weatherapp.utils;

import com.example.weatherapp.interfaces.DataSource;
import com.example.weatherapp.models.City;
import com.example.weatherapp.networks.FakeDataSource;
import com.example.weatherapp.models.TemperatureUnits;

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

    private List<City> selectedCities = new ArrayList<>();
    private int currentCityIndex;

    private final DataSource dataSource;

    // Конструктор (вызывать извне его нельзя, поэтому он приватный)
    private MainPresenter(){
        dataSource = new FakeDataSource();
        if (selectedCities.size() == 0) {
            addCity("Moscow");
            addCity("Saint Petersburg");
            addCity("London");
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

    public TemperatureUnits getTUnit() {
        return tUnit;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public List<City> getSelectedCities() {
        return selectedCities;
    }

    public boolean cityIsInList(String name) {
        for (City city : selectedCities) {
            if (city.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addCity(String name) {
        if (cityIsInList(name)) {
            return;
        }
        selectedCities.add(new City(name, dataSource));
    }

    public boolean cityIsInList(City city) {
        return selectedCities.contains(city);
    }

    public void deleteCity(City city) {
        selectedCities.remove(city);
        if (this.city.equals(city.getName()) && selectedCities.size() > 0) {
            this.city = selectedCities.get(0).getName();
        }
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
