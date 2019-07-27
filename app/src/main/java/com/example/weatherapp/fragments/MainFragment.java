package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.utils.MainPresenter;

public class MainFragment extends Fragment {

    private final MainPresenter presenter = MainPresenter.getInstance();

    private TextView twCity;
    private TextView twTUnit;
    private LinearLayout pressure;
    private LinearLayout wind;
    private LinearLayout feelsLike;

   public MainFragment() {
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        twCity = view.findViewById(R.id.city);
        twTUnit = view.findViewById(R.id.unit);
        pressure = view.findViewById(R.id.pressure);
        wind = view.findViewById(R.id.wind);
        feelsLike = view.findViewById(R.id.feels_like);

        updateView();
    }

    public void updateView() {
        twCity.setText(presenter.getCity());
        twTUnit.setText(presenter.gettUnit().toString());

        pressure.setVisibility(visibility(presenter.isShowPressure()));
        wind.setVisibility(visibility(presenter.isShowWind()));
        feelsLike.setVisibility(visibility(presenter.isShowFeelsLike()));
    }

    private int visibility(boolean visible) {
        if (visible) {
            return View.VISIBLE;
        }
        return View.GONE;
    }


}
