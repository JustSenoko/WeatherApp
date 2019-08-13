package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.services.WeatherProviderService;
import com.example.weatherapp.utils.ConfSingleton;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements ObserverWeatherInfo {

    private SelectedCities selectedCities;
    private UserPreferences userPreferences;

    private OnMainFragmentListener mListener;
    private SensorEventListener listenerTemperature;
    private SensorEventListener listenerHumidity;

    private TextView twCity;
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
    private LinearLayout sensorLayout;
    private LinearLayout sensorTemperatureLayout;
    private TextView twSensorHumidityValue;
    private LinearLayout sensorHumidityLayout;
    private TextView twSensorTemperatureValue;
    private TextView twSensorTemperatureUnit;
    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;

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
        ((MainActivity) Objects.requireNonNull(getActivity())).getPublisher().subscribeWeatherInfo(this);
        userPreferences = new UserPreferences(Objects.requireNonNull(getActivity()));
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        initFields(view);

        if (selectedCities.getCurrentCity() == null) {
            mListener.openCitySelectionFragment();
            return;
        }
        initSensors();
        loadWeatherInfo();
    }

    private void initFields(@NonNull View view) {
        twCity = view.findViewById(R.id.city);
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
        sensorLayout = view.findViewById(R.id.sensor_data);
        sensorTemperatureLayout = view.findViewById(R.id.sensor_temperature);
        sensorHumidityLayout = view.findViewById(R.id.sensor_humidity);
        twSensorTemperatureValue = view.findViewById(R.id.sensor_temperature_value);
        twSensorTemperatureUnit = view.findViewById(R.id.sensor_temperature_unit);
        twSensorHumidityValue = view.findViewById(R.id.sensor_humidity_value);
    }

    private void initSensors() {
        boolean showTemperatureSensorInfo = initTemperatureSensor();
        sensorTemperatureLayout.setVisibility(visibility(showTemperatureSensorInfo));
        boolean showHumiditySensorInfo = initHumiditySensor();
        sensorHumidityLayout.setVisibility(visibility(showHumiditySensorInfo));
        sensorLayout.setVisibility(visibility(showTemperatureSensorInfo || showHumiditySensorInfo));
    }

    private boolean initTemperatureSensor() {
        boolean showSensorTemperature = userPreferences.isShowSensorTemperature();
        if (!showSensorTemperature) {
            return false;
        }
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sensorTemperature == null) {
            return false;
        }
        listenerTemperature = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                updateSensorTemperature(event);
            }
        };
        sensorManager.registerListener(listenerTemperature, sensorTemperature,
                SensorManager.SENSOR_DELAY_NORMAL);

        return true;
    }

    private boolean initHumiditySensor() {
        boolean showSensorHumidity = userPreferences.isShowSensorHumidity();
        if (!showSensorHumidity) {
            return false;
        }
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (sensorHumidity == null) {
            return false;
        }
        listenerHumidity = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                updateSensorHumidity(event);
            }
        };
        sensorManager.registerListener(listenerHumidity, sensorHumidity,
                SensorManager.SENSOR_DELAY_NORMAL);

        return true;
    }

    @SuppressLint("DefaultLocale")
    private void updateSensorHumidity(SensorEvent event) {
        twSensorHumidityValue.setText(String.format("%.0f", event.values[0]));
    }

    @SuppressLint("DefaultLocale")
    private void updateSensorTemperature(SensorEvent event) {
        float value = event.values[0];
        if (userPreferences.useImperialUnits()) {
            value = Units.convertCelsiusToFahrenheit(value);
        }
        twSensorTemperatureValue.setText(String.format("%.0f", value));
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemperature, sensorTemperature);
        sensorManager.unregisterListener(listenerHumidity, sensorHumidity);
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
                    18 + i, 176 + (i % 2), 95, 3, "cloudy"));
        }
        return items;
    }

    private void updateCurrentWeatherData(final String cityName) {
        String units = Units.getUnitsName(userPreferences.useImperialUnits());
        WeatherProviderService.startWeatherLoad(getActivity(), cityName, units);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void updateWeatherInfo(WeatherItem currentWeather) {
        if (currentWeather == null) {
            showErrorMessage();
            return;
        }
        twTemperatureValue.setText(String.format("%d", currentWeather.getTemperature()));
        tvHumidityValue.setText(String.format("%d", currentWeather.getHumidity()));
        twPressureValue.setText(String.valueOf((Integer) currentWeather.getPressure()));
        twWindValue.setText(String.format("%2.1f", currentWeather.getWind()));
        twWeather.setText(currentWeather.getWeather());
    }

    private void showErrorMessage() {
        Snackbar.make(twCity, R.string.err_city_not_found, Snackbar.LENGTH_SHORT);
    }

    private void updateWeatherRepresentationSettings() {
        twCity.setText(selectedCities.getCurrentCity().getName());
        String temperatureUnit = Units.getTemperatureUnit(userPreferences.useImperialUnits());
        twTemperatureUnit.setText(temperatureUnit);
        twSensorTemperatureUnit.setText(temperatureUnit);
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
        City currentCity = selectedCities.getCurrentCity();
        if (currentCity != null) {
            updateCurrentWeatherData(currentCity.getName());
            updateWeatherRepresentationSettings();
        }
    }
}
