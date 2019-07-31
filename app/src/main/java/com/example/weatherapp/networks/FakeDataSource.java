package com.example.weatherapp.networks;

import com.example.weatherapp.interfaces.DataSource;
import com.example.weatherapp.models.WeatherItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FakeDataSource implements DataSource {

    private List<String> cities;

    public FakeDataSource() {
        loadCitiesList();
    }

    private void loadCitiesList() {
        cities = new ArrayList<>();
        cities.add("Moscow");
        cities.add("London");
        cities.add("Saint Petersburg");
        cities.add("New York");
        cities.add("Rome");
        cities.add("Milan");
    }

    @Override
    public boolean findCity(String cityName) {
        return cities.contains(cityName);
    }

    @Override
    public boolean loadCityWeather(String cityName) {
        return true;
    }

    @Override
    public WeatherItem getWeather(String city, Date date) {
        Random rn = new Random();
        int temperature = 15 + rn.nextInt(15);
        return new WeatherItem(date,
                temperature,
                736,
                rn.nextInt(20),
                temperature + rn.nextInt(4) - 2,
                "sunny");
    }
}
