package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.CitySelectionFragment;
import com.example.weatherapp.fragments.MainFragment;
import com.example.weatherapp.fragments.SettingsFragment;
import com.example.weatherapp.interfaces.PublishGetter;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.models.pojo.City;
import com.example.weatherapp.utils.ConfSingleton;
import com.example.weatherapp.utils.Publisher;
import com.example.weatherapp.utils.UserPreferences;

public class MainActivity extends BaseActivity
        implements CitySelectionFragment.OnFragmentCitySelectionListener,
            MainFragment.OnMainFragmentListener,
            SettingsFragment.OnSettingsFragmentListener,
            PublishGetter {
    private Publisher publisher = new Publisher();
    private UserPreferences userPreferences;
    private SelectedCities selectedCities;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainFragment mainFragment = new MainFragment();
    private CitySelectionFragment citySelectionFragment = new CitySelectionFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_main);
        if (savedInstanceState==null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment, mainFragment)
                    .addToBackStack("")
                    .commit();
        }
    }

    private void init() {
        userPreferences = new UserPreferences(this);
        selectedCities = userPreferences.getSelectedCities();

        ConfSingleton conf = ConfSingleton.getInstance();
        //TODO настроить после урока по БД
        //conf.setCitiesData(new CityDataJSON(this));
        conf.setSelectedCities(selectedCities);
        mainFragment = new MainFragment();
        publisher.subscribeWeatherInfo(mainFragment);
        citySelectionFragment = new CitySelectionFragment();
        publisher.subscribeCityList(citySelectionFragment);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        recreate();
    }

    @Override
    public void onSelectCity(City city) {
        userPreferences.setCurrentCityId(city.getId());
        selectedCities.setCurrentCity(city);
        fragmentManager.popBackStack();
        //publisher.notifyUpdateWeatherInfo();
    }

    @Override
    public void onDeleteCity(City city) {
        publisher.notifyDeleteCity(city);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void openCitySelectionFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment, citySelectionFragment)
            .addToBackStack("")
            .commit();
    }

    public void changeCity(MenuItem item) {
        openCitySelectionFragment();
    }

    @Override
    public void onThemeChanged() {
        recreate();
    }

    @Override
    public void onSettingsChanged() {
        //TODO
    }
}
