package com.example.weatherapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.R;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String NAME_SHARED_PREFERENCES = "Weather_App";
    private static final String IS_DARK_THEME = "darkTheme";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTheme();
    }

    private void setCustomTheme() {
        if (isDarkTheme()) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }

    protected boolean isDarkTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_DARK_THEME, false);
    }

    protected void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPreferences = getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_DARK_THEME, isDarkTheme);
        editor.apply();
    }
}
