package com.example.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final MainPresenter presenter = MainPresenter.getInstance();

    private Switch showPressure;
    private Switch showWind;
    private Switch showFeelsLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToast("On create");
        setContentView(R.layout.settings);
//        setContentView(R.layout.city_selection);

        showPressure = findViewById(R.id.show_pressure);
        showWind = findViewById(R.id.show_wind);
        showFeelsLike = findViewById(R.id.show_feels_like);

        restoreSettingsValues();

        final Spinner spinner = findViewById(R.id.spinner_units);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.temp_units));
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
    }

    private void restoreSettingsValues() {
        showPressure.setChecked(presenter.isShowPressure());
        showWind.setChecked(presenter.isShowWind());
        showFeelsLike.setChecked(presenter.isShowFeelsLike());
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d("settings", msg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToast("On start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("On stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("On destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        showToast("On pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("On resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showToast("On restart");
    }

    public void deleteCity0(View view) {
        deleteCityFromList(0);
    }

    public void deleteCity1(View view) {
        deleteCityFromList(1);
    }

    private boolean deleteCityFromList(int index) {
        //TODO delete from list
        //TODO delete table row
        return true;
    }

    public void deleteCity2(View view) {
        deleteCityFromList(2);
    }

    public void selectCity0(View view) {
        selectCity(0);
    }

    public void selectCity1(View view) {
        selectCity(1);
    }

    public void selectCity2(View view) {
        selectCity(2);
    }

    private boolean selectCity(int index) {
        //TODO set city selected
        //TODO color table row
        //TODO update info
        return true;
    }

    public void addCity(View view) {
        //TODO add city to list
        //TODO add table row
        selectCity(3);
    }

    public void selectCity(View view) {
        //TODO open city_selection
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
}
