package com.example.weatherapp.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.CityItemAdapter;
import com.example.weatherapp.interfaces.ObserverCityList;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.networks.WeatherDataLoader;
import com.example.weatherapp.utils.ConfSingleton;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentCitySelectionListener}
 * interface.
 */
public class CitySelectionFragment extends Fragment implements ObserverCityList {

    private TextInputEditText txtCityToAdd;

    private OnFragmentCitySelectionListener mListener;

    private ConfSingleton conf = ConfSingleton.getInstance();
    private UserPreferences userPreferences;
    private SelectedCities selectedCities = conf.getSelectedCities();
    private CityItemAdapter adapter;

    public interface OnFragmentCitySelectionListener {
        void onSelectCity(City city);
        void onDeleteCity(City city);
        void setVisibilityOfChangeCityMenuItem(boolean visible);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        userPreferences = new UserPreferences(Objects.requireNonNull(getActivity()));

        if (context instanceof OnFragmentCitySelectionListener) {
            mListener = (OnFragmentCitySelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentCitySelectionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener.setVisibilityOfChangeCityMenuItem(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCityToAdd = view.findViewById(R.id.city_to_add);
        initAddButton(view);
        initRecyclerView(view);
    }

    private void initAddButton(View view) {
        MaterialButton btnAdd = view.findViewById(R.id.add_city);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = Objects.requireNonNull(txtCityToAdd.getText()).toString();
                validateCityName(cityName);
            }
        });
    }

    private void initRecyclerView(View view) {
        RecyclerView rvCityList = view.findViewById(R.id.city_list);
        // Set the adapter
        if (rvCityList != null) {
            Context context = view.getContext();
            rvCityList.setLayoutManager(new LinearLayoutManager(context));
            adapter = new CityItemAdapter(selectedCities.getSelectedCitiesList(), mListener);
            rvCityList.setAdapter(adapter);

            Drawable divider = context.getResources().getDrawable(R.drawable.separator_horizontal);
            if (divider != null) {
                DividerItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL);
                itemDecoration.setDrawable(divider);
                rvCityList.addItemDecoration(itemDecoration);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.setVisibilityOfChangeCityMenuItem(true);
        mListener = null;
    }

    @Override
    public void deleteSelectedCity(City city) {
        if (!selectedCities.cityIsInList(city)) {
            Snackbar.make(txtCityToAdd, R.string.err_city_not_found, Snackbar.LENGTH_SHORT);
            return;
        }
        selectedCities.deleteCity(city);
        adapter.notifyDataSetChanged();
        updateUserPreferences();
    }

    private void addToSelectedCities(City city) {
        selectedCities.addCity(city);
        txtCityToAdd.setText("");
        adapter.notifyDataSetChanged();
        updateUserPreferences();
    }

    private void updateUserPreferences() {
        userPreferences.setSelectedCitiesJson(selectedCities);
    }

    private void validateCityName(String cityName) {
        if (cityName.isEmpty()) {
            showError(txtCityToAdd, getResources().getString(R.string.err_enter_city));
            return;
        }
        findCityByName(cityName);
    }

    private void showError(TextView tv, String message) {
        tv.setError(message);
    }

    private void hideError(TextView tv) {
        tv.setError(null);
    }

    private void findCityByName(final String cityName) {
        //TODO это лишний запрос через API список доступных городов есть в формате json,
        // переделать после урока по БД
        final String units = Units.getUnitsName(userPreferences.useImperialUnits());
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeatherDataLoader weatherDataSource = new WeatherDataLoader();
                final WeatherItem currentWeather = weatherDataSource.loadCurrentWeatherData(cityName, units);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentWeather == null) {
                            showError(txtCityToAdd, getResources().getString(R.string.err_city_not_found));
                            return;
                        }
                        City city = currentWeather.getCity();
                        if (selectedCities.cityIsInList(city)) {
                            showError(txtCityToAdd, getResources().getString(R.string.err_city_is_in_list));
                            return;
                        }
                        hideError(txtCityToAdd);
                        addToSelectedCities(city);
                    }
                });
            }
        }).start();
    }
}
