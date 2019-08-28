package com.blueroofstudio.weatherapp.adapters;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blueroofstudio.weatherapp.R;
import com.blueroofstudio.weatherapp.database.CitiesTable;
import com.blueroofstudio.weatherapp.fragments.CitySelectionFragment;
import com.blueroofstudio.weatherapp.models.restEntities.City;

import java.util.List;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.ViewHolder> {

    private final List<City> cities;
    private final SQLiteDatabase database;
    private final CitySelectionFragment.OnFragmentCitySelectionListener mListener;

    public CityItemAdapter(SQLiteDatabase database, CitySelectionFragment.OnFragmentCitySelectionListener listener) {
        this.database = database;
        mListener = listener;
        cities = CitiesTable.getSelectedCities(database);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.updateView(cities.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSelectCity(holder.mItem);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onDeleteCity(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public City getItem(int index) {
        return cities.get(index);
    }

    public boolean cityIsInList(City city) {
        return cities.contains(city);
    }
    public void deleteCity(City city) {
        cities.remove(city);
        CitiesTable.deselectCity(city, database);
        notifyDataSetChanged();
    }

    public void addCity(City city) {
        if (cityIsInList(city)) {
            return;
        }
        cities.add(city);
        CitiesTable.addCity(city, database);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private final TextView city;
        private final ImageButton delete;
        private City mItem;

        ViewHolder(View view) {
            super(view);
            itemView = view;
            city = view.findViewById(R.id.city_name);
            delete = view.findViewById(R.id.delete_city);
        }

        void updateView(City cityItem) {
            mItem = cityItem;
            city.setText(String.format("%s, %s", mItem.name, mItem.country));
        }
    }
}
