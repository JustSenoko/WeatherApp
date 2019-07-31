package com.example.weatherapp.utils;

import com.example.weatherapp.interfaces.DataSource;
import com.example.weatherapp.models.City;
import com.example.weatherapp.models.CurrentWeatherRequest;
import com.example.weatherapp.networks.FakeDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class MainPresenter {

    //Внутреннее поле, будет хранить единственный экземпляр
    private static MainPresenter instance = null;

    // Поле для синхронизации
    private static final Object syncObj = new Object();

    private CurrentWeatherRequest wr;
    private Date lastRequestTime;

    private List<City> selectedCities = new ArrayList<>();
    private int currentCityIndex;

    private final DataSource dataSource;
    private String city = "Moscow";

    // Конструктор (вызывать извне его нельзя, поэтому он приватный)
    private MainPresenter(){
        dataSource = new FakeDataSource();
        if (selectedCities.size() == 0) {
            addCity("Moscow");
            addCity("Saint Petersburg");
            addCity("London");
        }
        currentCityIndex = 0;
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

    public void setCity(String city) {
        this.city = city;
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

    public CurrentWeatherRequest getWr() {
        return wr;
    }

    public void setWr(CurrentWeatherRequest wr) {
        this.wr = wr;
        this.lastRequestTime = new Date();
    }

    public Date getLastRequestTime() {
        return lastRequestTime;
    }
}
