package com.example.topmovies.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.topmovies.R;
import com.example.topmovies.adapters.MoviesAdapter;
import com.example.topmovies.models.ResultMovies;
import com.example.topmovies.viewModels.MoviesViewModel;


public class MoviesFragment extends Fragment implements MoviesAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private MoviesViewModel moviesViewModel;
    private NavController controller;


    public MoviesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.moviesList);
        controller = Navigation.findNavController(view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        moviesViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        final MoviesAdapter adapter = new MoviesAdapter(this);
        moviesViewModel.pagedListLiveData.observe(getActivity(), resultMovies -> {
            adapter.submitList(resultMovies);

        });

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onMovieClicked(ResultMovies movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("object" , movie);
        bundle.putDouble("vote",movie.getVoteAverage());
        controller.navigate(R.id.action_moviesFragment_to_detailsFragment , bundle);
    }
}