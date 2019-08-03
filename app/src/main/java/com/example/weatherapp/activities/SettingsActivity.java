package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.utils.ConfSingleton;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends BaseActivity {

    private final static int CITY_SELECTION_REQUEST_CODE = 2;
    private static String SETTINGS_SAVED;

    private UserPreferences userPreferences;
    private SelectedCities selectedCities = ConfSingleton.getInstance().getSelectedCities();

    private Switch darkTheme;
    private TextView twCity;
    private Switch showPressure;
    private Switch showWind;
    private Switch imperialUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        userPreferences = new UserPreferences(this);
        SETTINGS_SAVED = getResources().getString(R.string.msg_settings_saved);

        darkTheme = findViewById(R.id.darkTheme);
        twCity = findViewById(R.id.current_city);
        showPressure = findViewById(R.id.show_pressure);
        showWind = findViewById(R.id.show_wind);
        imperialUnits = findViewById(R.id.units);

        restoreSettingsValues();
    }

    private void restoreSettingsValues() {
        darkTheme.setChecked(userPreferences.isDarkTheme());
        twCity.setText(selectedCities.getCurrentCity().getName());
        showPressure.setChecked(userPreferences.isShowPressure());
        showWind.setChecked(userPreferences.isShowWind());
        imperialUnits.setChecked(userPreferences.useImperialUnits());
    }

    public void showPressureOnClick(View view) {
        userPreferences.setShowPressure(showPressure.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    public void showWindOnClick(View view) {
        userPreferences.setShowWind(showWind.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    public void themeOnClick(View view) {
        userPreferences.setDarkTheme(darkTheme.isChecked());
        recreate();
    }

    public void imperialUnitsOnClick(View view) {
        userPreferences.setUseImperialUnits(imperialUnits.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    public void selectCity(View view) {
        Intent intent = new Intent(SettingsActivity.this, CitySelectionActivity.class);
        startActivityForResult(intent, CITY_SELECTION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CITY_SELECTION_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        twCity.setText(selectedCities.getCurrentCity().getName());
    }

    private void showResult(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }


}
