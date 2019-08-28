package com.blueroofstudio.weatherapp.interfaces;

import com.blueroofstudio.weatherapp.models.WeatherItem;

import java.util.List;

public interface WeatherDataSource {

    WeatherItem loadCurrentWeatherDataByCityName(String cityName, String units);
    WeatherItem loadCurrentWeatherDataByCityId(String cityId, String units);
    List<WeatherItem> loadWeatherForecastOn5Days(String city, String units);
}
