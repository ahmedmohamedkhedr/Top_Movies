package com.example.topmovies.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.topmovies.R;

public class MainFragment extends Fragment {
    private CardView movies, series, favourites , search;
    private NavController controller;

    public MainFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        movies = view.findViewById(R.id.movies);
        series = view.findViewById(R.id.series);
        favourites = view.findViewById(R.id.favourites);
        search = view.findViewById(R.id.search);
        movies.setOnClickListener(v -> controller.navigate(R.id.action_mainFragment_to_moviesFragment));
        series.setOnClickListener(v -> controller.navigate(R.id.action_mainFragment_to_tvShowsFragment));
        favourites.setOnClickListener(v -> controller.navigate(R.id.action_mainFragment_to_favouritesFragment));
        search.setOnClickListener(v->controller.navigate(R.id.action_mainFragment_to_searchFragment));
    }
}