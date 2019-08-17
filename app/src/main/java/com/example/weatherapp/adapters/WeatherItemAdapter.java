package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.Units;
import com.example.weatherapp.models.WeatherItem;
import com.example.weatherapp.utils.UserPreferences;
import com.example.weatherapp.utils.WeatherIconsFont;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.ViewHolder> {

    private final List<WeatherItem> items;
    private static final String DATE_FORMAT = "d MMM\nHH:mm";
    private final UserPreferences userPreferences;

    public WeatherItemAdapter(List<WeatherItem> items, UserPreferences userPreferences) {
        this.items = items;
        this.userPreferences = userPreferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_weather_item, parent, false);
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
        private final TextView weatherIcon;
        private final TextView temperatureValue;
        private final TextView temperatureUnit;
        private final TextView date;
        private WeatherItem item;

        ViewHolder(View view) {
            super(view);
            weatherIcon = view.findViewById(R.id.weather_icon);
            temperatureValue = view.findViewById(R.id.temperature_value);
            temperatureUnit = view.findViewById(R.id.unit);
            date = view.findViewById(R.id.date);
        }

        void updateView(WeatherItem weatherItem) {
            String unit = Units.getTemperatureUnit(userPreferences.useImperialUnits());
            item = weatherItem;
            weatherIcon.setText(WeatherIconsFont.getWeatherIcon(itemView.getContext(), item.getWeatherId()));
            temperatureValue.setText(String.valueOf(item.getTemperature()));
            temperatureUnit.setText(unit);
            date.setText(getDateFormatted(item.getDate()));
        }
    }
}
