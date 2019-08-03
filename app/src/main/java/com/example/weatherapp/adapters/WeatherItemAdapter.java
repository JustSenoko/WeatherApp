package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.WeatherItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.ViewHolder> {

    private List<WeatherItem> items;
    private static final String DATE_FORMAT = "d MMM";

    public WeatherItemAdapter(List<WeatherItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.updateView(items.get(position));
    }

    private String getDateFormatted(Date date) {
         return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView temperature;
        private final TextView weather;
        private final TextView date;
        private WeatherItem item;

        ViewHolder(View view) {
            super(view);
            temperature = view.findViewById(R.id.temperature_value);
            weather = view.findViewById(R.id.weather);
            date = view.findViewById(R.id.date);
        }

        void updateView(WeatherItem weatherItem) {
            item = weatherItem;
            temperature.setText(String.valueOf(item.getTemperature()));
            weather.setText(String.valueOf(item.getWeather()));
            date.setText(getDateFormatted(item.getDate()));
        }
    }
}
