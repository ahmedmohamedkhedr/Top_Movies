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

import com.example.topmovies.R;
import com.example.topmovies.adapters.SeriesAdapter;
import com.example.topmovies.models.ResultSeries;
import com.example.topmovies.viewModels.SeriesViewModel;

public class TvShowsFragment extends Fragment implements SeriesAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private SeriesViewModel seriesViewModel;
    private NavController controller;


    public TvShowsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.seriesList);
        controller = Navigation.findNavController(view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 2));
        seriesViewModel = ViewModelProviders.of(getActivity()).get(SeriesViewModel.class);
        final SeriesAdapter adapter = new SeriesAdapter(this);
        seriesViewModel.pagedListLiveData.observe(getActivity(), resultSeries -> adapter.submitList(resultSeries));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onTVClicked(ResultSeries tv) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("object" , tv);
        bundle.putDouble("vote",tv.getVoteAverage());
        controller.navigate(R.id.action_tvShowsFragment_to_detailsFragment , bundle);

    }
}