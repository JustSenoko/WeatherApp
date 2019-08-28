package com.blueroofstudio.weatherapp.networks;

import com.blueroofstudio.weatherapp.interfaces.IOpenWeather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class OpenWeatherRepo {
    private static OpenWeatherRepo singleton = null;
    private IOpenWeather API;

    private OpenWeatherRepo() {
        API = createAdapter();
    }

    IOpenWeather getAPI() {
        return API;
    }

    private IOpenWeather createAdapter() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(IOpenWeather.class);
    }

    static OpenWeatherRepo getInstance() {
        if (singleton == null) {
            singleton = new OpenWeatherRepo();
        }
        return singleton;
    }
}
