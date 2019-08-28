package com.blueroofstudio.weatherapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blueroofstudio.weatherapp.R;
import com.blueroofstudio.weatherapp.utils.UserPreferences;

public abstract class BaseActivity extends AppCompatActivity {

    private UserPreferences userPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreferences = new UserPreferences(this);
        setCustomTheme();
    }

    private void setCustomTheme() {
        if (userPreferences.isDarkTheme()) {
            setTheme(R.style.DarkTheme_NoActionBar);
        } else {
            setTheme(R.style.LightTheme_NoActionBar);
        }
    }


}
