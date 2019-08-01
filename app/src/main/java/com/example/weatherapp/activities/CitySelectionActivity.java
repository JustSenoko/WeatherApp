package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.CitiesFragment;
import com.example.weatherapp.models.City;
import com.example.weatherapp.models.CurrentWeatherRequest;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.networks.WeatherDataLoader;
import com.example.weatherapp.utils.MainPresenter;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CitySelectionActivity extends BaseActivity
        implements CitiesFragment.OnSelectCityListener, CitiesFragment.OnDeleteCityListener {

    private final MainPresenter presenter = MainPresenter.getInstance();
    private TextInputEditText txtCityToAdd;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_selection);
        userPreferences = new UserPreferences(this);
        txtCityToAdd = findViewById(R.id.city_to_add);
    }

    public void addCity(View view) {
        String cityName = Objects.requireNonNull(txtCityToAdd.getText()).toString();
        if (!validateCityName(cityName)) {
            return;
        }
        loadWeather(cityName);
    }

    private boolean validateCityName(final String cityName) {
        if (cityName.isEmpty()) {
            showError(txtCityToAdd, getResources().getString(R.string.err_enter_city));
            return false;
        }
        if (presenter.cityIsInList(cityName)) {
            showError(txtCityToAdd, getResources().getString(R.string.err_city_is_in_list));
            return false;
        }
        return true;
    }

    private void loadWeather(final String cityName) {
        final String units = Units.getUnitsName(userPreferences.useImperialUnits());
        final String errText = getResources().getString(R.string.err_city_not_found);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final CurrentWeatherRequest wr = WeatherDataLoader.loadCurrentWeatherData(cityName, units);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (wr == null) {
                            showError(txtCityToAdd, errText);
                        } else if (!wr.loadedSuccessful()) {
                            showError(txtCityToAdd, wr.getMessage());
                        } else {
                            presenter.setWr(wr);
                            hideError(txtCityToAdd);
                            addCityToList(cityName);
                        }
                    }
                });
            }
        }).start();
    }

    private void addCityToList(String cityName) {
        presenter.addCity(cityName);
        txtCityToAdd.setText("");
        updateCityList();
    }

    private void showError(TextView tv, String message) {
        tv.setError(message);
    }

    private void hideError(TextView tv) {
        tv.setError(null);
    }

    @Override
    public void onSelectCity(City item) {
        userPreferences.setCurrentCity(item.getName());
        presenter.setCity(item.getName());
        Intent intentResult = new Intent();
        setResult(RESULT_OK, intentResult);
        finish();
    }

    @Override
    public void onDeleteCity(City city) {
        if (!presenter.cityIsInList(city)) {
            Snackbar.make(txtCityToAdd, R.string.err_city_not_found, Snackbar.LENGTH_SHORT);
            return;
        }
        presenter.deleteCity(city);
        updateCityList();
    }

    private void updateCityList() {
        FragmentManager fm = getSupportFragmentManager();
        CitiesFragment f = (CitiesFragment) fm.findFragmentById(R.id.fragment_selected_cities);
        if (f != null) {
            f.invalidate();
        }
        //TODO select item
    }
}
