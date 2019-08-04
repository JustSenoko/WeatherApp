package com.example.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    private static String SETTINGS_SAVED;

    private UserPreferences userPreferences;
    private OnSettingsFragmentListener mListener;

    private Switch darkTheme;
    private Switch showPressure;
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SETTINGS_SAVED = getResources().getString(R.string.msg_settings_saved);

        initViews(view);
        restoreSettingsValues();
        setSwitchListeners();
        return view;
    }

    private void initViews(View view) {
        darkTheme = view.findViewById(R.id.darkTheme);
        showPressure = view.findViewById(R.id.show_pressure);
        showWind = view.findViewById(R.id.show_wind);
        imperialUnits = view.findViewById(R.id.units);
    }

    private void restoreSettingsValues() {
        darkTheme.setChecked(userPreferences.isDarkTheme());
        showPressure.setChecked(userPreferences.isShowPressure());
        showWind.setChecked(userPreferences.isShowWind());
        imperialUnits.setChecked(userPreferences.useImperialUnits());
    }

    private void setSwitchListeners() {
        darkTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                themeOnCheckedChanged(darkTheme);
            }
        });

        showPressure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPressureOnCheckedChanged(showPressure);
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

    private void showPressureOnCheckedChanged(View view) {
        userPreferences.setShowPressure(showPressure.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void showWindOnCheckedChanged(View view) {
        userPreferences.setShowWind(showWind.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void themeOnCheckedChanged(View view) {
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
