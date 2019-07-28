package com.example.weatherapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.weatherapp.fragments.CitiesFragment;
import com.example.weatherapp.models.City;
import com.example.weatherapp.R;

import java.util.List;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.ViewHolder> {

    private final List<City> cities;
    private final CitiesFragment.OnSelectCityListener mSelectListener;
    private final CitiesFragment.OnDeleteCityListener mDeleteListener;

    public CityItemAdapter(List<City> cities, CitiesFragment.OnSelectCityListener selectListener,
                           CitiesFragment.OnDeleteCityListener deleteListener) {
        this.cities = cities;
        mSelectListener = selectListener;
        mDeleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = cities.get(position);
        holder.city.setText(cities.get(position).getName());
        holder.temperature.setText(String.valueOf(cities.get(position).getCurrentTemperature()));
        holder.weather.setText(String.valueOf(cities.get(position).getCurrentWeather()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mSelectListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mSelectListener.onSelectCity(holder.mItem);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mDeleteListener) {
                    mDeleteListener.onDeleteCity(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        public final TextView city;
        final TextView temperature;
        final TextView weather;
        final ImageButton delete;
        City mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            city = view.findViewById(R.id.city_name);
            temperature = view.findViewById(R.id.city_temperature);
            weather = view.findViewById(R.id.city_weather);
            delete = view.findViewById(R.id.deleteCity);
        }
    }
}
