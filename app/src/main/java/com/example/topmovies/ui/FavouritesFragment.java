package com.example.topmovies.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.topmovies.R;
import com.example.topmovies.adapters.FavoritesAdapter;
import com.example.topmovies.dataBase.DatabaseClient;
import com.example.topmovies.dataBase.FavouriteItem;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouritesFragment extends Fragment implements FavoritesAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    FavoritesAdapter adapter;


    public FavouritesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favourites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewFavourites();
    }

    void viewFavourites() {
        Observable.create(emitter -> {
            emitter.onNext(DatabaseClient.getInstance(getContext()).getDataBase().getDAO().getAllFavourites());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    adapter = new FavoritesAdapter((List<FavouriteItem>) list, this);
                    recyclerView.setAdapter(adapter);
                });
    }


    @Override
    public void onItemClicked(FavouriteItem item, int startPosition, int endPosition) {
        Observable.just(item)
                .map(item1 -> {
                    DatabaseClient.getInstance(FavouritesFragment.this.getContext()).getDataBase().getDAO().deleteFromFavourite(item1);
                    return "Item Deleted from Favourites";
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    adapter.notifyItemRemoved(startPosition);
                    adapter.notifyItemRangeChanged(startPosition,endPosition);
                    Toast.makeText(FavouritesFragment.this.getContext(), s, Toast.LENGTH_SHORT).show();

                });
    }
}