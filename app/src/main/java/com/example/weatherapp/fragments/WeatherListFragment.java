package com.example.weatherapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.WeatherItemAdapter;
import com.example.weatherapp.models.WeatherItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class WeatherListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            boolean horizontal = context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE;
            LinearLayoutManager layout = new LinearLayoutManager(context);
            layout.setOrientation(horizontal ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(new WeatherItemAdapter(getWeatherItems()));

            int separator = (horizontal ? R.drawable.separator_vertical : R.drawable.separator_horizontal);
            Drawable divider = context.getDrawable(separator);
            if (divider != null) {
                DividerItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), layout.getOrientation());
                itemDecoration.setDrawable(divider);
                recyclerView.addItemDecoration(itemDecoration);
            }
        }
        return view;
    }

    private List<WeatherItem> getWeatherItems() {
        Calendar calendar = new GregorianCalendar();
        List<WeatherItem> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            items.add(new WeatherItem(calendar.getTime(),
                    18 + i, 176 + (i % 2), 3, 18 - i, "cloudy"));
        }
        return items;
    }
}
