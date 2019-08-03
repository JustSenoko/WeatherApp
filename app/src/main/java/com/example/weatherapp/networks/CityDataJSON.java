package com.example.weatherapp.networks;

import com.example.weatherapp.interfaces.CitiesDataSource;
import com.example.weatherapp.models.pojo.City;

import java.util.ArrayList;
import java.util.List;

//Singleton
public class CityDataJSON implements CitiesDataSource {

    private final List<City> cities = new ArrayList<>();

    //Внутреннее поле, будет хранить единственный экземпляр
    private static CityDataJSON instance = null;
    // Поле для синхронизации
    private static final Object syncObj = new Object();

    private CityDataJSON() {
        //TODO read json from res
        cities.add(new City("Moscow", 524901, "RU"));
    }

    public static CityDataJSON getInstance(){
        synchronized (syncObj) {
            if (instance == null) {
                instance = new CityDataJSON();
            }
            return instance;
        }
    }

    @Override
    public City findCityByName(String cityName) {
        //TODO
        return null;
    }
}
