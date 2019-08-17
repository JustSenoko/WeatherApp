package com.example.weatherapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.models.pojo.CurrentWeatherData;
import com.example.weatherapp.models.pojo.WeatherForecast;

import java.util.Date;

public class WeatherItem implements Parcelable {

    private final Date date;
    private final City city;
    private final int temperature;
    private final int pressure;
    private final int humidity;
    private final float wind;
    private final String weather;
    private final int weatherId;

    public WeatherItem(CurrentWeatherData currentWeather) {
        date = currentWeather.getDate();
        city = new City(currentWeather.getName(), currentWeather.getId());
        temperature = (int) currentWeather.getMain().getTemp();
        pressure = (int) currentWeather.getMain().getPressure();
        humidity = (int) currentWeather.getMain().getHumidity();
        wind = (int) currentWeather.getWind().getSpeed();
        weather = currentWeather.getWeather().getDescription();
        weatherId = currentWeather.getWeather().getId();
    }

    public WeatherItem(City city, WeatherForecast weatherForecast) {
        this.city = city;
        date = weatherForecast.getDate();
        temperature = (int) weatherForecast.getMain().getTemp();
        pressure = (int) weatherForecast.getMain().getPressure();
        humidity = (int) weatherForecast.getMain().getHumidity();
        wind = weatherForecast.getWind().getSpeed();
        weather = weatherForecast.getWeather().getDescription();
        weatherId = weatherForecast.getWeather().getId();
    }

    public WeatherItem(Date date, City city, int temperature, int pressure, int humidity,
                       float wind, String weather, int weatherId) {
        this.date = date;
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.weather = weather;
        this.weatherId = weatherId;
    }

    private WeatherItem(Parcel parcel) {
        date = (Date) parcel.readSerializable();
        city = (City) parcel.readSerializable();
        temperature = parcel.readInt();
        pressure = parcel.readInt();
        humidity = parcel.readInt();
        wind = parcel.readFloat();
        weather = parcel.readString();
        weatherId = parcel.readInt();
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

    public float getWind() {
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
        dest.writeFloat(wind);
        dest.writeString(weather);
        dest.writeInt(weatherId);
    }
}
