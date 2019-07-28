package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.utils.MainPresenter;

public class SettingsActivity extends BaseActivity {

    private final MainPresenter presenter = MainPresenter.getInstance();
    private final static int CITY_SELECTION_REQUEST_CODE = 2;

    private Switch darkTheme;
    private TextView twCity;
    private Spinner spUnit;
    private Switch showPressure;
    private Switch showWind;
    private Switch showFeelsLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        darkTheme = findViewById(R.id.theme);
        twCity = findViewById(R.id.current_city);
        showPressure = findViewById(R.id.show_pressure);
        showWind = findViewById(R.id.show_wind);
        showFeelsLike = findViewById(R.id.show_feels_like);
        spUnit = findViewById(R.id.spinner_units);

        restoreSettingsValues();

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.temp_units));
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spUnit.setAdapter(adapter);
    }

    private void restoreSettingsValues() {
        darkTheme.setChecked(isDarkTheme());
        twCity.setText(presenter.getCity());
        showPressure.setChecked(presenter.isShowPressure());
        showWind.setChecked(presenter.isShowWind());
        showFeelsLike.setChecked(presenter.isShowFeelsLike());
        //spUnit.setSelection(0); //TODO переделать после урока по ресурсам
    }

    public void showPressureOnClick(View view) {
        presenter.setShowPressure(showPressure.isChecked());
    }

    public void showWindOnClick(View view) {

        presenter.setShowWind(showWind.isChecked());
    }

    public void showFeelsLikeOnClick(View view) {
        presenter.setShowFeelsLike(showFeelsLike.isChecked());
    }

    public void themeOnClick(View view) {
        setDarkTheme(darkTheme.isChecked());
        recreate();
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
        twCity.setText(presenter.getCity());
    }
}
