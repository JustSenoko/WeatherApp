package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.CitiesFragment;
import com.example.weatherapp.models.City;
import com.example.weatherapp.utils.MainPresenter;

import java.util.Objects;

public class CitySelectionActivity extends AppCompatActivity
        implements CitiesFragment.OnSelectCityListener, CitiesFragment.OnDeleteCityListener {

    private final MainPresenter presenter = MainPresenter.getInstance();
    private TextInputEditText txtCityToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_selection);

        txtCityToAdd = findViewById(R.id.city_to_add);
    }

    public void addCity(View view) {
        String cityName = Objects.requireNonNull(txtCityToAdd.getText()).toString();

        if (!validateCityName(cityName)) {
            return;
        }
        hideError(txtCityToAdd);

        presenter.addCity(cityName);
        txtCityToAdd.setText("");

        updateCityList();
    }

    private boolean validateCityName(String cityName) {
        if (cityName.isEmpty()) {
            showError(txtCityToAdd, getResources().getString(R.string.err_enter_city));
            return false;
        }
        if (presenter.cityIsInList(cityName)) {
            showError(txtCityToAdd, getResources().getString(R.string.err_city_is_in_list));
            return false;
        }
        if (!presenter.getDataSource().findCity(cityName)) {
            showError(txtCityToAdd, getResources().getString(R.string.err_city_not_found));
            return false;
        }
        return true;
    }

    private void showError(TextView tv, String message) {
        tv.setError(message);
    }

    private void hideError(TextView tv) {
        tv.setError(null);
    }

    @Override
    public void onSelectCity(City item) {
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
