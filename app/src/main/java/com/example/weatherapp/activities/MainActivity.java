package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.AboutAppFragment;
import com.example.weatherapp.fragments.CitySelectionFragment;
import com.example.weatherapp.fragments.FeedbackFragment;
import com.example.weatherapp.fragments.MainFragment;
import com.example.weatherapp.fragments.SettingsFragment;
import com.example.weatherapp.interfaces.PublishGetter;
import com.example.weatherapp.models.SelectedCities;
import com.example.weatherapp.models.restEntities.City;
import com.example.weatherapp.utils.ConfSingleton;
import com.example.weatherapp.utils.Publisher;
import com.example.weatherapp.utils.UserPreferences;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CitySelectionFragment.OnFragmentCitySelectionListener,
        MainFragment.OnMainFragmentListener,
        SettingsFragment.OnSettingsFragmentListener,
        PublishGetter {

    private final Publisher publisher = new Publisher();
    private UserPreferences userPreferences;
    private SelectedCities selectedCities;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final MainFragment mainFragment = new MainFragment();
    private final CitySelectionFragment citySelectionFragment = new CitySelectionFragment();
    private final SettingsFragment settingsFragment = new SettingsFragment();

    private static long back_pressed;
    private boolean showChangeCityMenuItem = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawerLayout(toolbar);
        initUtils();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment, mainFragment)
                    .addToBackStack("")
                    .commit();
        }
    }

    private void initDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initUtils() {
        userPreferences = new UserPreferences(this);
        selectedCities = userPreferences.getSelectedCities();
        ConfSingleton conf = ConfSingleton.getInstance();

        conf.setSelectedCities(selectedCities);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        menu.findItem(R.id.menu_change_city).setVisible(showChangeCityMenuItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_change_city) {
            openCitySelectionFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            openFragment(mainFragment);
        } else if (id == R.id.nav_settings) {
            openFragment(settingsFragment);
        } else if (id == R.id.nav_share) {
            //TODO
            Toast.makeText(this, getResources().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_feedback) {
            openFragment(new FeedbackFragment());
        } else if (id == R.id.nav_about) {
            openFragment(new AboutAppFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        recreate();
    }

    @Override
    public void onSelectCity(City city) {
        selectedCities.setCurrentCity(city);
        userPreferences.setSelectedCities(selectedCities);
        fragmentManager.popBackStack();
    }

    @Override
    public void onDeleteCity(City city) {
        publisher.notifyDeleteCity(city);
    }

    @Override
    public void setVisibilityOfChangeCityMenuItem(boolean visible) {
        showChangeCityMenuItem = visible;
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void openCitySelectionFragment() {
        openFragment(citySelectionFragment);
    }

    private void openFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onThemeChanged() {
        recreate();
    }
}
