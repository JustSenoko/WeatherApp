package com.example.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
//        setContentView(R.layout.city_selection);

        final Spinner spinner = findViewById(R.id.spinner_units);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.temp_units));
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
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
}
