package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.weatherapp.adapters.WeatherItemAdapter;
import com.example.weatherapp.interfaces.WeatherDataSource;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.networks.WeatherDataLoader;
import com.example.weatherapp.utils.ConfSingleton;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {

    private SelectedCities selectedCities;
    private UserPreferences userPreferences;

    private OnMainFragmentListener mListener;

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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        selectedCities = ConfSingleton.getInstance().getSelectedCities();
        initWeatherList(view);
        return view;
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
            rvWeatherList.setAdapter(new WeatherItemAdapter(getWeatherItems()));

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
        userPreferences = new UserPreferences(Objects.requireNonNull(getActivity()));
        twCity = view.findViewById(R.id.city);
        twTemperatureValue = view.findViewById(R.id.temperature_value);
        twTemperatureUnit = view.findViewById(R.id.unit);
        pressure = view.findViewById(R.id.pressure);
        twPressureValue = view.findViewById(R.id.pressure_value);
        wind = view.findViewById(R.id.wind);
        twWindUnit = view.findViewById(R.id.wind_unit);
        twWindValue = view.findViewById(R.id.wind_value);
        twWeather = view.findViewById(R.id.city_weather);

        if (selectedCities.getCurrentCity() == null) {
            mListener.openCitySelectionFragment();
            return;
        }
        updateWeatherInfo();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private List<WeatherItem> getWeatherItems() {
        //TODO replace fake data
        Calendar calendar = new GregorianCalendar();
        List<WeatherItem> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            items.add(new WeatherItem(calendar.getTime(),
                    selectedCities.getCurrentCity(),
                    18 + i, 176 + (i % 2), 3, "cloudy"));
        }
        return items;
    }

    private void updateCurrentWeatherData(final String cityName) {
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

    private void updateWeatherInfo() {
        City currentCity = selectedCities.getCurrentCity();
        if (currentCity != null) {
            updateCurrentWeatherData(currentCity.getName());
            updateView();
        }
    }
}
