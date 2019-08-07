package com.example.weatherapp.networks;

import android.content.Context;

import com.example.weatherapp.interfaces.CitiesDataSource;
import com.example.weatherapp.models.pojo.City;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CityDataJSON implements CitiesDataSource {

    private final Context context;
    private List<City> cities = new ArrayList<>();
    private static final String assetFileName = "city.list.json";

    public CityDataJSON(Context context) {
        this.context = context;
        //TODO залить json в БД (сейчас файл в assets, считывается очень долго, нельзя использовать)
        //readJSONFromAsset(context);
    }

    private void readJSONFromAsset(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in = context.getAssets().open(assetFileName);
                    int size = in.available();
                    byte[] buffer = new byte[size];
                    in.read(buffer);
                    in.close();
                    String jsonCityList = new String(buffer, StandardCharsets.UTF_8);
                    Gson gson = new Gson();
                    cities = Arrays.asList(gson.fromJson(jsonCityList, City[].class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
