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
import com.example.weatherapp.interfaces.WeatherDataSource;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.networks.WeatherDataLoader;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

class MainFragment extends Fragment {

    private final SelectedCities selectedCities = SelectedCities.getInstance();
    private UserPreferences userPreferences;

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
        updateWeatherData(selectedCities.getCurrentCity().getName());
        updateView();
    }

    private void updateWeatherData(final String cityName) {
        final WeatherDataSource weatherDataSource = new WeatherDataLoader();
        final String units = Units.getUnitsName(userPreferences.useImperialUnits());
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final WeatherItem currentWeather = weatherDataSource.loadCurrentWeatherData(cityName, units);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentWeather == null) {
                            showErrorMessage();
                        } else {
                            updateWeatherInfo(currentWeather);
                        }
                    }
                });
            }
        }).start();
    }

    @SuppressLint("DefaultLocale")
    private void updateWeatherInfo(WeatherItem currentWeather) {
        twTemperatureValue.setText(String.format("%d", currentWeather.getTemperature()));
        twPressureValue.setText(String.valueOf((Integer) currentWeather.getPressure()));
        twWindValue.setText(String.format("%2.1f", currentWeather.getWind()));
        twWeather.setText(currentWeather.getWeather());
    }

    private void showErrorMessage() {
        Snackbar.make(twCity, R.string.err_city_not_found, Snackbar.LENGTH_SHORT);
    }

    private void updateView() {
        twCity.setText(selectedCities.getCurrentCity().getName());
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
