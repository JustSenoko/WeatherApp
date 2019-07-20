package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final MainPresenter presenter = MainPresenter.getInstance();
    private final static int SETTINGS_REQUEST_CODE = 1;

    TextView twCity;
    TextView twTUnit;
    LinearLayout pressure;
    LinearLayout wind;
    LinearLayout feelsLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twCity = findViewById(R.id.city);
        twTUnit = findViewById(R.id.unit);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        feelsLike = findViewById(R.id.feels_like);

        updateView();
    }

    private void updateView() {
        twCity.setText(presenter.getCity());
        twTUnit.setText(presenter.gettUnit());

        pressure.setVisibility(visibility(presenter.isShowPressure()));
        wind.setVisibility(visibility(presenter.isShowWind()));
        feelsLike.setVisibility(visibility(presenter.isShowFeelsLike()));
    }

    private int visibility(boolean visible) {
        if (visible) {
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public void onSettingsClick(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != SETTINGS_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        updateView();
    }

}
