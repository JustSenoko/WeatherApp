package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.CitySelectionFragment;
import com.example.weatherapp.models.pojo.City;

import java.util.List;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.ViewHolder> {

    private final List<City> cities;
    private final CitySelectionFragment.OnFragmentCitySelectionListener mListener;

    public CityItemAdapter(List<City> cities, CitySelectionFragment.OnFragmentCitySelectionListener listener) {
        this.cities = cities;
        mListener = listener;
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
            city.setText(mItem.getName());
        }
    }
}
