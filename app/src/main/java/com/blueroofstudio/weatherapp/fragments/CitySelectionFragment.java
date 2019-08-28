package com.blueroofstudio.weatherapp.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.blueroofstudio.weatherapp.R;
import com.blueroofstudio.weatherapp.activities.MainActivity;
import com.blueroofstudio.weatherapp.adapters.CityItemAdapter;
import com.blueroofstudio.weatherapp.database.DatabaseHelper;
import com.blueroofstudio.weatherapp.interfaces.ObserverCityList;
import com.blueroofstudio.weatherapp.models.restEntities.City;
import com.blueroofstudio.weatherapp.networks.WeatherDataLoader;
import com.blueroofstudio.weatherapp.utils.Publisher;
import com.blueroofstudio.weatherapp.utils.UserPreferences;
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

    private UserPreferences userPreferences;
    private Publisher publisher;
    private CityItemAdapter adapter;
    private SQLiteDatabase database;
    private RecyclerView rvCityList;

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.setVisibilityOfChangeCityMenuItem(false);
        publisher = ((MainActivity) Objects.requireNonNull(getActivity())).getPublisher();
        publisher.subscribeCityList(this);
        txtCityToAdd = view.findViewById(R.id.city_to_add);
        initDB();
        initAddButton(view);
        initRecyclerView(view);
    }

    private void initDB() {
        database = new DatabaseHelper(getContext()).getWritableDatabase();
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
        rvCityList = view.findViewById(R.id.city_list);
        // Set the adapter
        if (rvCityList != null) {
            Context context = view.getContext();
            rvCityList.setLayoutManager(new LinearLayoutManager(context));
            adapter = new CityItemAdapter(database, mListener);
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
        publisher.unsubscribeCityList(this);
        mListener.setVisibilityOfChangeCityMenuItem(true);
        mListener = null;
    }

    @Override
    public void deleteSelectedCity(City city) {
        if (!adapter.cityIsInList(city)) {
            Snackbar.make(txtCityToAdd, R.string.err_city_not_found, Snackbar.LENGTH_SHORT);
            return;
        }
        adapter.deleteCity(city);

        if (userPreferences.getCurrentCityId() == city.id) {
            City currentCity = adapter.getItem(0);
            userPreferences.setCurrentCityId(currentCity == null ? 0 : currentCity.id);
        }
    }

    private void validateCityName(String cityName) {
        if (cityName.isEmpty()) {
            showError(txtCityToAdd, getResources().getString(R.string.err_enter_city));
            return;
        }
        findCityByName(cityName);
    }

    private void findCityByName(final String cityName) {
        WeatherDataLoader.findCityByName(publisher, cityName);
    }

    @Override
    public void findCityByNameResult(City city) {
        if (city == null) {
            showError(txtCityToAdd, getResources().getString(R.string.err_city_not_found));
            return;
        }
        if (adapter.cityIsInList(city)) {
            showError(txtCityToAdd, getResources().getString(R.string.err_city_is_in_list));
            return;
        }
        hideError(txtCityToAdd);
        addToSelectedCities(city);
    }

    private void addToSelectedCities(City city) {
        adapter.addCity(city);
        txtCityToAdd.setText("");
        if (userPreferences.getCurrentCityId() == 0) {
            userPreferences.setCurrentCityId(city.id);
        }
        scrollToLastItem();
    }

    private void scrollToLastItem() {
        int itemCount = adapter.getItemCount();
        if (itemCount == 0) {
            return;
        }
        rvCityList.smoothScrollToPosition(itemCount - 1);
    }

    private void showError(TextView tv, String message) {
        tv.setError(message);
    }

    private void hideError(TextView tv) {
        tv.setError(null);
    }
}
