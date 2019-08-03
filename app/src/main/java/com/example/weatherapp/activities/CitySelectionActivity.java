package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.CitiesFragment;
import com.example.weatherapp.interfaces.CitiesDataSource;
import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.networks.CityDataJSON;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CitySelectionActivity extends BaseActivity
        implements CitiesFragment.OnSelectCityListener, CitiesFragment.OnDeleteCityListener {

    private final SelectedCities selectedCities = SelectedCities.getInstance();
    private final CitiesDataSource citiesDataSource = CityDataJSON.getInstance();
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
        if (cityName.isEmpty()) {
            showError(txtCityToAdd, getResources().getString(R.string.err_enter_city));
            return;
        }
        City city = citiesDataSource.findCityByName(cityName);
        if (city == null) {
            showError(txtCityToAdd, getResources().getString(R.string.err_city_not_found));
            return;
        }
        if (selectedCities.cityIsInList(city)) {
            showError(txtCityToAdd, getResources().getString(R.string.err_city_is_in_list));
            return;
        }
        hideError(txtCityToAdd);
        addCityToList(city);
    }

    private void addCityToList(City city) {
        selectedCities.addCity(city);
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
        userPreferences.setCurrentCityId(item.getId());
        selectedCities.setCurrentCity(item);
        Intent intentResult = new Intent();
        setResult(RESULT_OK, intentResult);
        finish();
    }

    @Override
    public void onDeleteCity(City city) {
        if (!selectedCities.cityIsInList(city)) {
            Snackbar.make(txtCityToAdd, R.string.err_city_not_found, Snackbar.LENGTH_SHORT);
            return;
        }
        selectedCities.deleteCity(city);
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
