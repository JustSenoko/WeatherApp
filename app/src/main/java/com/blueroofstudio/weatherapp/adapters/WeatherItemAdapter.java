package com.blueroofstudio.weatherapp.adapters;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blueroofstudio.weatherapp.R;
import com.blueroofstudio.weatherapp.database.WeatherTable;
import com.blueroofstudio.weatherapp.models.Units;
import com.blueroofstudio.weatherapp.models.WeatherItem;
import com.blueroofstudio.weatherapp.utils.UserPreferences;
import com.blueroofstudio.weatherapp.utils.WeatherIconsFont;
import com.blueroofstudio.weatherapp.utils.WeatherIconsFresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.ViewHolder> {

    private final SQLiteDatabase database;
    private List<WeatherItem> items;
    private static final String DATE_FORMAT = "d MMM";
    private final UserPreferences userPreferences;

    public WeatherItemAdapter(SQLiteDatabase database, UserPreferences userPreferences) {
        this.database = database;
        this.items = WeatherTable.getDailyTemperatureForecast(userPreferences.getCurrentCityId(), database);
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

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateForecast(int cityId) {
        items = WeatherTable.getDailyTemperatureForecast(cityId, database);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView weatherIcon;
        private final TextView temperatureValue;
        private final TextView temperatureUnit;
        private final TextView date;
        private WeatherItem item;
        private SimpleDraweeView frescoWeatherIcon;

        ViewHolder(View view) {
            super(view);
            weatherIcon = view.findViewById(R.id.weather_icon);
            frescoWeatherIcon = view.findViewById(R.id.weather_icon_fresco);
            temperatureValue = view.findViewById(R.id.temperature_value);
            temperatureUnit = view.findViewById(R.id.unit);
            date = view.findViewById(R.id.date);
        }

        void updateView(WeatherItem weatherItem) {
            String unit = Units.getTemperatureUnit(userPreferences.useImperialUnits());
            item = weatherItem;
            weatherIcon.setText(WeatherIconsFont.getWeatherIcon(itemView.getContext(), item.getWeatherId()));
            frescoWeatherIcon.setImageURI(WeatherIconsFresco.getWeatherIcon(item.getWeatherIcon()));
            temperatureValue.setText(String.valueOf(item.getTemperature()));
            temperatureUnit.setText(unit);
            date.setText(getLocalDateFromUTC(item.getDate()));
        }
    }

    private String getLocalDateFromUTC(Date dateUTC) {
            TimeZone timeZone = TimeZone.getDefault();
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            dateFormatter.setTimeZone(timeZone);
            return dateFormatter.format(dateUTC);
    }
}