package com.example.weatherapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.weatherapp.models.restEntities.City;
import com.example.weatherapp.models.restEntities.CurrentWeatherData;
import com.example.weatherapp.models.restEntities.Main;
import com.example.weatherapp.models.restEntities.Weather;
import com.example.weatherapp.models.restEntities.WeatherForecast;

import java.util.Date;

public class WeatherItem implements Parcelable {

    private final Date date;
    private final City city;
    private final int temperature;
    private final int pressure;
    private final int humidity;
    private final int wind;
    private final String weather;
    private final int weatherId;
    private final String weatherIcon;

    public WeatherItem(CurrentWeatherData currentWeather) {
        date = currentWeather.date;
        city = new City(currentWeather.name, currentWeather.id, currentWeather.sys.country);
        Main main = currentWeather.main;
        temperature = (int) main.temp;
        pressure = (int) main.pressure;
        humidity = (int) main.humidity;
        wind = (int) currentWeather.wind.speed;
        Weather restWeather = currentWeather.getWeather();
        weather = restWeather.description;
        weatherId = restWeather.id;
        weatherIcon = restWeather.icon;
    }

    public WeatherItem(City city, WeatherForecast weatherForecast) {
        this.city = city;
        date = weatherForecast.getDate();
        Main main = weatherForecast.main;
        temperature = (int) main.temp;
        pressure = (int) main.pressure;
        humidity = (int) main.humidity;
        wind = (int) weatherForecast.wind.speed;
        Weather restWeather = weatherForecast.getWeather();
        weather = restWeather.description;
        weatherId = restWeather.id;
        weatherIcon = restWeather.icon;
    }

    private WeatherItem(Parcel parcel) {
        date = (Date) parcel.readSerializable();
        city = (City) parcel.readSerializable();
        temperature = parcel.readInt();
        pressure = parcel.readInt();
        humidity = parcel.readInt();
        wind = parcel.readInt();
        weather = parcel.readString();
        weatherId = parcel.readInt();
        weatherIcon = parcel.readString();
    }

    public static final Parcelable.Creator<WeatherItem> CREATOR = new Parcelable.Creator<WeatherItem>() {
        public WeatherItem createFromParcel(Parcel parcel) {
            return new WeatherItem(parcel);
        }

        public WeatherItem[] newArray(int size) {
            return new WeatherItem[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getWind() {
        return wind;
    }

    public String getWeather() {
        return weather;
    }

    public City getCity() {
        return city;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(date);
        dest.writeSerializable(city);
        dest.writeInt(temperature);
        dest.writeInt(pressure);
        dest.writeInt(humidity);
        dest.writeInt(wind);
        dest.writeString(weather);
        dest.writeInt(weatherId);
        dest.writeString(weatherIcon);
    }
}
