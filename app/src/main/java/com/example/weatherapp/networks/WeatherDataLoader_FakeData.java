package com.example.weatherapp.networks;

import com.example.weatherapp.interfaces.WeatherDataSource;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.utils.ConfSingleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class WeatherDataLoader_FakeData implements WeatherDataSource {

    @Override
    public WeatherItem loadCurrentWeatherData(String cityName, String units) {
        City city = new City("Moscow", 524901, "RU");
        if (cityName.equals("Riga")) {
            city = new City("Riga", 456172, "LV");
        } else if (cityName.equals("London")){
            city = new City("London", 2643743, "GB");
        }
        return new WeatherItem(new Date(), city,
                18, 176, 98, 1, "rain", 500);
    }

    @Override
    public List<WeatherItem> loadWeatherForecastOn5Days(String cityName, String units) {
        List<WeatherItem> items = new ArrayList<>();

        Calendar calendar = new GregorianCalendar();
        SelectedCities selectedCities = ConfSingleton.getInstance().getSelectedCities();
        City city = selectedCities.getCurrentCity();
        for (int i = 0; i < 10; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            items.add(new WeatherItem(calendar.getTime(), city,
                    18 + i, 176 + (i % 2), 95, 3, "cloudy", 802));
        }
        return items;
    }
}
