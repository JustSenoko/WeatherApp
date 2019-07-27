package com.example.weatherapp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.service.Weather;

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
            layout.setOrientation(horizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
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

    private List<Weather> getWeatherItems() {
        Calendar calendar = new GregorianCalendar();
        List<Weather> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            items.add(new Weather(calendar.getTime(),
                    18 + i, 176 + (i % 2), 3, 18 - i, "cloudy"));
        }
        return items;
    }
}
