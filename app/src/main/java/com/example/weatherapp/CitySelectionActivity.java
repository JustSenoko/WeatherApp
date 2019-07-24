package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.weatherapp.service.MainPresenter;

public class CitySelectionActivity extends AppCompatActivity {

    private final MainPresenter presenter = MainPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_selection);

        restoreSettingsValues();

    }

    private void restoreSettingsValues() {
        //TODO переделать после урока по ресурсам
//        if (presenter.getCity().equals(getResources().getString(R.string.city))) {
//
//        } else if (index == 1) {
//            presenter.setCity(getResources().getString(R.string.city1));
//        }
//        else if (index == 2) {
//            presenter.setCity(getResources().getString(R.string.city2));
//        }

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
        selectCity(view, 0);
    }

    public void selectCity1(View view) {
        selectCity(view,1);
    }

    public void selectCity2(View view) {
        selectCity(view,2);
    }

    private void selectCity(View view, int index) {
        //TODO переделать после урока по ресурсам
        if (index == 0) {
            presenter.setCity(getResources().getString(R.string.city));
        } else if (index == 1) {
            presenter.setCity(getResources().getString(R.string.city1));
        }
        else if (index == 2) {
            presenter.setCity(getResources().getString(R.string.city2));
        } else {
            return;
        }
        //TODO update info
        Intent intentResult = new Intent();
        setResult(RESULT_OK, intentResult);
        finish();
    }

    public void addCity(View view) {
        //TODO add city to list
        //TODO add table row
        selectCity(view, 3);
    }
}
