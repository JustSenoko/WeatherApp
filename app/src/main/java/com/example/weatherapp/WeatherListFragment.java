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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WeatherListFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
//    private int size = 3;
//    private boolean horizontal = false;

//    public static WeatherListFragment create(int size, boolean horizontalOrientation) {
//        WeatherListFragment f = new WeatherListFragment();    // создание
//
//        // Передача параметра
//        Bundle args = new Bundle();
//        args.putInt(weatherListSizeKey, size);
//        args.putBoolean(weatherListOrientationKey, horizontalOrientation);
//        f.setArguments(args);
//        return f;
//    }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Weather item);
    }
}
