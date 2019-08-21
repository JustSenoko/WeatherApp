package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.adapters.WeatherItemAdapter;
import com.example.weatherapp.interfaces.ObserverWeatherInfo;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.networks.WeatherDataLoader;
import com.example.weatherapp.utils.Publisher;
import com.example.weatherapp.utils.UserPreferences;
import com.example.weatherapp.utils.WeatherIconsFont;
import com.example.weatherapp.utils.WeatherIconsFresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements ObserverWeatherInfo {

    private UserPreferences userPreferences;
    private Publisher publisher;

    private OnMainFragmentListener mListener;

    private WeatherItemAdapter adapter;
    private final List<WeatherItem> forecast = new ArrayList<>();

    private TextView twCity;
    private TextView twWeatherIcon;
    private SimpleDraweeView frescoWeatherIcon;
    private TextView twTemperatureUnit;
    private TextView twTemperatureValue;
    private LinearLayout pressure;
    private TextView twPressureValue;
    private LinearLayout wind;
    private TextView twWindUnit;
    private TextView twWindValue;
    private TextView twWeather;
    private LinearLayout humidity;
    private TextView tvHumidityValue;


    public interface OnMainFragmentListener {
        void openCitySelectionFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainFragment.OnMainFragmentListener) {
            mListener = (MainFragment.OnMainFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainFragmentListener");
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_activity_menu, menu);
    }

    private void initWeatherList(View view) {
        RecyclerView rvWeatherList = view.findViewById(R.id.weather_list);
        if (rvWeatherList != null) {
            Context context = view.getContext();
            LinearLayoutManager layout = new LinearLayoutManager(context);
            rvWeatherList.setLayoutManager(layout);
            adapter = new WeatherItemAdapter(forecast, userPreferences);
            rvWeatherList.setAdapter(adapter);

            Drawable divider = context.getResources().getDrawable(R.drawable.separator_horizontal);
            if (divider != null) {
                DividerItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL);
                itemDecoration.setDrawable(divider);
                rvWeatherList.addItemDecoration(itemDecoration);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        publisher = ((MainActivity) Objects.requireNonNull(getActivity())).getPublisher();
        publisher.subscribeWeatherInfo(this);
        userPreferences = new UserPreferences(Objects.requireNonNull(getActivity()));

        initFields(view);
        initWeatherList(view);

        if (userPreferences.getCurrentCityId() == 0) {
            mListener.openCitySelectionFragment();
            return;
        }
        loadWeatherInfo();
    }

    private void initFields(@NonNull View view) {
        twCity = view.findViewById(R.id.city);
        twWeatherIcon = view.findViewById(R.id.weather_icon);
        frescoWeatherIcon = view.findViewById(R.id.weather_icon_fresco);
        twTemperatureValue = view.findViewById(R.id.temperature_value);
        twTemperatureUnit = view.findViewById(R.id.unit);
        pressure = view.findViewById(R.id.pressure);
        twPressureValue = view.findViewById(R.id.pressure_value);
        humidity = view.findViewById(R.id.humidity);
        tvHumidityValue = view.findViewById(R.id.humidity_value);
        wind = view.findViewById(R.id.wind);
        twWindUnit = view.findViewById(R.id.wind_unit);
        twWindValue = view.findViewById(R.id.wind_value);
        twWeather = view.findViewById(R.id.city_weather);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateCurrentWeatherData(int cityId, Publisher publisher) {
        String units = Units.getUnitsName(userPreferences.useImperialUnits());
        WeatherDataLoader.loadCurrentWeatherDataByCityId(publisher, cityId, units);
    }

    private void updateWeatherForecast(final int cityId, Publisher publisher) {
        String units = Units.getUnitsName(userPreferences.useImperialUnits());
        WeatherDataLoader.loadWeatherForecastOn5Days(publisher, cityId, units);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void updateCurrentWeatherViews(WeatherItem currentWeather) {
        if (currentWeather == null) {
            showErrorMessage();
            return;
        }
        twCity.setText(currentWeather.getCity().name);
        twWeatherIcon.setText(WeatherIconsFont.getWeatherIcon(Objects.requireNonNull(getContext()), currentWeather.getWeatherId()));
        frescoWeatherIcon.setImageURI(WeatherIconsFresco.getWeatherIcon(currentWeather.getWeatherIcon()));
        twTemperatureValue.setText(String.format("%d", currentWeather.getTemperature()));
        tvHumidityValue.setText(String.format("%d", currentWeather.getHumidity()));
        twPressureValue.setText(String.valueOf((Integer) currentWeather.getPressure()));
        twWindValue.setText(String.format("%d", currentWeather.getWind()));
        twWeather.setText(currentWeather.getWeather());

        updateWeatherRepresentationSettings();
    }

    @Override
    public void updateWeatherForecastViews(List<WeatherItem> forecastUpd) {
        if (forecastUpd == null) {
            showErrorMessage();
            return;
        }
        forecast.clear();
        forecast.addAll(forecastUpd);
        adapter.notifyDataSetChanged();
    }

    private void showErrorMessage() {
        Snackbar.make(twCity, R.string.err_city_not_found, Snackbar.LENGTH_SHORT);
    }

    private void updateWeatherRepresentationSettings() {
        String temperatureUnit = Units.getTemperatureUnit(userPreferences.useImperialUnits());
        twTemperatureUnit.setText(temperatureUnit);
        pressure.setVisibility(visibility(userPreferences.isShowPressure()));
        humidity.setVisibility(visibility(userPreferences.isShowHumidity()));
        twWindUnit.setText(getResources().getString(Units.getWindUnit(userPreferences.useImperialUnits())));
        wind.setVisibility(visibility(userPreferences.isShowWind()));
    }

    private int visibility(boolean visible) {
        if (visible) {
            return View.VISIBLE;
        }
        return View.GONE;
    }

    private void loadWeatherInfo() {
        int cityId = userPreferences.getCurrentCityId();
        if (cityId != 0) {
            updateCurrentWeatherData(cityId, publisher);
            updateWeatherForecast(cityId, publisher);
        }
    }
}
