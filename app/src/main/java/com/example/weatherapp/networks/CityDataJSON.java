package com.example.weatherapp.networks;

import android.content.Context;

import com.example.weatherapp.interfaces.CitiesDataSource;
import com.example.weatherapp.models.pojo.City;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CityDataJSON implements CitiesDataSource {

    private final List<City> cities = new ArrayList<>();
    private static final String assetFileName = "city.list.json";

    public CityDataJSON(Context context) {
        String json = readJSONFromAsset(context);
        if (json == null) {
            cities.add(new City("Moscow", 524901, "RU"));
        }
    }

    private String readJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream in = context.getAssets().open(assetFileName);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public List<City> findCityByName(String cityName) {
        List<City> result = new ArrayList<>();
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                result.add(city);
            }
        }
        return result;
    }
}
