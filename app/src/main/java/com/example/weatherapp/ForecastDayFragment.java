package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class ForecastDayFragment extends Fragment {

    public ForecastDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @SuppressLint("DefaultLocale")
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;

        ScrollView scroll = new ScrollView(getContext());
        layoutView.addView(scroll);

        LinearLayout layoutWithScroll = new LinearLayout(getContext());
        layoutWithScroll.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(layoutWithScroll);

        String[] forecast_temp = getResources().getStringArray(R.array.forecast_temp);
        String temp_unit = getResources().getString(R.string.unit);

        for (int i = 0; i < 24; i++) {
            LinearLayout layoutTemp = new LinearLayout(getContext());
            layoutTemp.setOrientation(LinearLayout.HORIZONTAL);
            layoutTemp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView tvHour = new TextView(getContext());
            tvHour.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tvHour.setText(String.format("%02d:00", i));
            tvHour.setTextSize(24);
            tvHour.setGravity(Gravity.START);
            layoutTemp.addView(tvHour);

            TextView tvTemp = new TextView(getContext());
            tvTemp.setText(String.format("      %s  °%s", forecast_temp[i], temp_unit));
            tvTemp.setTextSize(24);
            tvTemp.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tvTemp.setGravity(Gravity.END);
            layoutTemp.addView(tvTemp);

            layoutWithScroll.addView(layoutTemp);
        }
    }
}
