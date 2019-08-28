package com.example.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    private static String SETTINGS_SAVED;

    private UserPreferences userPreferences;
    private OnSettingsFragmentListener mListener;

    private Switch currentLocation;
    private Switch darkTheme;
    private Switch showPressure;
    private Switch showHumidity;
    private Switch showWind;
    private Switch imperialUnits;

    public interface OnSettingsFragmentListener {
        void onThemeChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        userPreferences = new UserPreferences(Objects.requireNonNull(getActivity()));

        if (context instanceof OnSettingsFragmentListener) {
            mListener = (OnSettingsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSettingsFragmentListener");
        }
        SETTINGS_SAVED = getResources().getString(R.string.msg_settings_saved);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        restoreSettingsValues();
        setSwitchListeners();
    }

    private void initViews(View view) {
        currentLocation = view.findViewById(R.id.current_location);
        darkTheme = view.findViewById(R.id.darkTheme);
        showPressure = view.findViewById(R.id.show_pressure);
        showHumidity = view.findViewById(R.id.show_humidity);
        showWind = view.findViewById(R.id.show_wind);
        imperialUnits = view.findViewById(R.id.units);
    }

    private void restoreSettingsValues() {
        currentLocation.setChecked(userPreferences.useCurrentLocation());
        darkTheme.setChecked(userPreferences.isDarkTheme());
        showPressure.setChecked(userPreferences.isShowPressure());
        showHumidity.setChecked(userPreferences.isShowHumidity());
        showWind.setChecked(userPreferences.isShowWind());
        imperialUnits.setChecked(userPreferences.useImperialUnits());
    }

    private void setSwitchListeners() {
        currentLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentLocationOnCheckedChanged(currentLocation);
            }
        });

        darkTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                themeOnCheckedChanged();
            }
        });

        showPressure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPressureOnCheckedChanged(showPressure);
            }
        });

        showHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showHumidityOnCheckedChanged(showHumidity);
            }
        });

        showWind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showWindOnCheckedChanged(showWind);
            }
        });

        imperialUnits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imperialUnitsOnCheckedChanged(imperialUnits);
            }
        });
    }

    private void currentLocationOnCheckedChanged(View view) {
        userPreferences.setUseCurrentLocation(currentLocation.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void showPressureOnCheckedChanged(View view) {
        userPreferences.setShowPressure(showPressure.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void showHumidityOnCheckedChanged(View view) {
        userPreferences.setShowHumidity(showHumidity.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void showWindOnCheckedChanged(View view) {
        userPreferences.setShowWind(showWind.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void themeOnCheckedChanged() {
        userPreferences.setDarkTheme(darkTheme.isChecked());
        mListener.onThemeChanged();
    }

    private void imperialUnitsOnCheckedChanged(View view) {
        userPreferences.setUseImperialUnits(imperialUnits.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void showResult(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }

}
