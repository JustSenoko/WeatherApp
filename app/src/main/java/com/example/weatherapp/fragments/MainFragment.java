package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.models.CurrentWeatherRequest;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.weather.Weather;
import com.example.weatherapp.networks.WeatherDataLoader;
import com.example.weatherapp.utils.MainPresenter;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.Objects;

public class MainFragment extends Fragment {

    private UserPreferences userPreferences;
    private static final int WEATHER_VALID_DURATION = 5 * 60 * 1000; // 5 min

    private TextView twCity;
    private TextView twTemperatureUnit;
    private TextView twTemperatureValue;
    private LinearLayout pressure;
    private TextView twPressureValue;
    private LinearLayout wind;
    private TextView twWindUnit;
    private TextView twWindValue;
    private TextView twWeather;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        twCity = view.findViewById(R.id.city);
        twTemperatureValue = view.findViewById(R.id.temperature_value);
        twTemperatureUnit = view.findViewById(R.id.unit);
        pressure = view.findViewById(R.id.pressure);
        twPressureValue = view.findViewById(R.id.pressure_value);
        wind = view.findViewById(R.id.wind);
        twWindUnit = view.findViewById(R.id.wind_unit);
        twWindValue = view.findViewById(R.id.wind_value);
        twWeather = view.findViewById(R.id.city_weather);

        userPreferences = new UserPreferences(Objects.requireNonNull(getActivity()));
        updateWeatherData();
        updateView();
    }

    private void updateWeatherData() {
        MainPresenter presenter = MainPresenter.getInstance();
        if (weatherIsValid(presenter)) {
            updateWeatherInfo(presenter.getWr());
            return;
        }
        final String cityName = userPreferences.getCurrentCity();
        final String units = Units.getUnitsName(userPreferences.useImperialUnits());
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final CurrentWeatherRequest wr = WeatherDataLoader.loadCurrentWeatherData(cityName, units);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (wr == null) {
                            showErrorMessage();
                        } else {
                            updateWeatherInfo(wr);
                        }
                    }
                });
            }
        }).start();
    }

    private boolean weatherIsValid(MainPresenter presenter) {
        if (presenter.getWr() == null) {
            return false;
        }
        return presenter.getWr().getName().equals(userPreferences.getCurrentCity())
                && (presenter.getLastRequestTime().getTime() - new Date().getTime()) < WEATHER_VALID_DURATION;
    }

    @SuppressLint("DefaultLocale")
    private void updateWeatherInfo(CurrentWeatherRequest wr) {
        twTemperatureValue.setText(String.format("%2.0f", wr.getMain().getTemp()));
        twPressureValue.setText(String.valueOf((Integer) wr.getMain().getPressure()));
        twWindValue.setText(String.format("%2.1f", wr.getWind().getSpeed()));
        Weather weather = wr.getWeather();
        twWeather.setText(weather == null ? "" : weather.getDescription());
    }

    private void showErrorMessage() {
        Snackbar.make(twCity, R.string.err_connection_failed, Snackbar.LENGTH_SHORT);
    }

    private void updateView() {
        twCity.setText(userPreferences.getCurrentCity());
        twTemperatureUnit.setText(Units.getTemperatureUnit(userPreferences.useImperialUnits()));

        pressure.setVisibility(visibility(userPreferences.isShowPressure()));

        twWindUnit.setText(getResources().getString(Units.getWindUnit(userPreferences.useImperialUnits())));
        wind.setVisibility(visibility(userPreferences.isShowWind()));
    }

    private int visibility(boolean visible) {
        if (visible) {
            return View.VISIBLE;
        }
        return View.GONE;
    }


}
