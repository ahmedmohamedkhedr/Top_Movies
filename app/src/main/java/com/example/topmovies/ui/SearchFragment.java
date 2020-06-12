package com.example.topmovies.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.topmovies.R;
import com.example.topmovies.adapters.SearchAdapter;
import com.example.topmovies.models.PageMovies;
import com.example.topmovies.models.PageSearching;
import com.example.topmovies.models.SearchingResults;
import com.example.topmovies.repo.Client;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static io.reactivex.rxjava3.core.Observable.create;

public class SearchFragment extends Fragment implements SearchAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText searchEditText;
    private NavController controller;

    public SearchFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEditText = view.findViewById(R.id.searchField);
        recyclerView = view.findViewById(R.id.searchList);
        progressBar = view.findViewById(R.id.searchProgress);
        controller = Navigation.findNavController(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        searching(searchEditText);

    }

    private void searching(EditText searchEditText) {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                Log.d(TAG, "onNext: " + s);
                Client.getInstance().searchResults(s).enqueue(new Callback<PageSearching>() {
                    @Override
                    public void onResponse(Call<PageSearching> call, Response<PageSearching> response) {
                        if (response.body()!=null){
                            SearchAdapter adapter = new SearchAdapter(response.body().getResults() , SearchFragment.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<PageSearching> call, Throwable t) {

                    }
                });

             }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable observable = create(emitter -> {
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0) {
                        emitter.onNext(s.toString());
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }).subscribeOn(Schedulers.io());

        observable.observeOn(AndroidSchedulers.mainThread())
                .debounce(3, TimeUnit.SECONDS)
                .subscribe(observer);

    }

    @Override
    public void onItemClicked(SearchingResults result) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("object" , result);
        bundle.putDouble("vote" , result.getVoteAverage());
        controller.navigate(R.id.action_searchFragment_to_detailsFragment , bundle);

    }
}