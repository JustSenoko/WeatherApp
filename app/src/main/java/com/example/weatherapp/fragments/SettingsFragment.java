package com.example.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.utils.ConfSingleton;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    private static String SETTINGS_SAVED;

    private UserPreferences userPreferences;
    private SelectedCities selectedCities = ConfSingleton.getInstance().getSelectedCities();
    private OnSettingsFragmentListener mListener;

    private Switch darkTheme;
    private Switch showPressure;
    private Switch showWind;
    private Switch imperialUnits;

    public interface OnSettingsFragmentListener {
        void onThemeChanged();
        void onSettingsChanged();
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

        darkTheme = view.findViewById(R.id.darkTheme);
        showPressure = view.findViewById(R.id.show_pressure);
        showWind = view.findViewById(R.id.show_wind);
        imperialUnits = view.findViewById(R.id.units);

        restoreSettingsValues();
        return view;
    }

    private void restoreSettingsValues() {
        darkTheme.setChecked(userPreferences.isDarkTheme());
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
        mListener.onThemeChanged();
    }

    public void imperialUnitsOnClick(View view) {
        userPreferences.setUseImperialUnits(imperialUnits.isChecked());
        showResult(view, SETTINGS_SAVED);
    }

    private void showResult(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }

}
