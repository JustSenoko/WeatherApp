package com.example.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.CityItemAdapter;
import com.example.weatherapp.models.City;
import com.example.weatherapp.utils.MainPresenter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnSelectCityListener}
 * interface.
 */
public class CitiesFragment extends Fragment {

    private OnSelectCityListener mSelectListener;
    private OnDeleteCityListener mDeleteListener;
    private final MainPresenter presenter = MainPresenter.getInstance();
    private CityItemAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CitiesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CitiesFragment newInstance(int columnCount) {
        return new CitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new CityItemAdapter(getCities(), mSelectListener, mDeleteListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private List<City> getCities() {
        return presenter.getSelectedCities();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectCityListener) {
            mSelectListener = (OnSelectCityListener) context;
            mDeleteListener = (OnDeleteCityListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSelectCityListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSelectListener = null;
        mDeleteListener = null;
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
    public interface OnSelectCityListener {
        void onSelectCity(City item);
    }

    public interface OnDeleteCityListener {
        void onDeleteCity(City item);
    }

    public void invalidate() {
        adapter.notifyDataSetChanged();
    }
}
