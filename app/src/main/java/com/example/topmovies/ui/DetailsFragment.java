package com.example.topmovies.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topmovies.R;
import com.example.topmovies.dataBase.DAO;
import com.example.topmovies.dataBase.DatabaseClient;
import com.example.topmovies.dataBase.FavouriteItem;
import com.example.topmovies.models.ResultMovies;
import com.example.topmovies.models.ResultSeries;
import com.example.topmovies.models.SearchingResults;
import com.example.topmovies.repo.Links;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsFragment extends Fragment implements View.OnClickListener {
    private RingProgressBar ringProgressBar;
    private TextView title, date, overView;
    private ImageView poster, favouriteBtn;
    private double vote;
    private String uri;
    private double progress = 0;
    boolean isFavourite = false;


    public DetailsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ringProgressBar = view.findViewById(R.id.resultVote);
        title = view.findViewById(R.id.resultTitle);
        date = view.findViewById(R.id.resultDate);
        overView = view.findViewById(R.id.resultOverview);
        poster = view.findViewById(R.id.resultPoster);
        favouriteBtn = view.findViewById(R.id.favouriteBtn);
        favouriteBtn.setOnClickListener(this);
        assert getArguments() != null;
        double vote = getArguments().getDouble("vote");
        Object object = getArguments().get("object");
        this.vote = vote;
        String itemTitle = viewData(object);
        voteAnimation(vote);
        checkItemIfIsFavourite(itemTitle);
    }

    @Override
    public void onClick(View v) {


        if (!isFavourite){
            FavouriteItem item = new FavouriteItem(title.getText().toString(),
                    overView.getText().toString()
                    , date.getText().toString()
                    , uri
                    , vote);
            addToFavourites(item);
        }else {
            removeFromFavourites(title.getText().toString());
        }

    }


    private String viewData(Object object) {
        if (object instanceof ResultMovies) {
            ResultMovies movie = (ResultMovies) object;
            Picasso.get()
                    .load(Links.IMAGE_PATH.concat(movie.getPosterPath()))
                    .placeholder(R.drawable.movies)
                    .into(poster);
            title.setText(movie.getOriginalTitle());
            overView.setText(movie.getOverview());
            date.setText(movie.getReleaseDate());
            uri = Links.IMAGE_PATH.concat(movie.getPosterPath());
            return movie.getOriginalTitle();

        } else if (object instanceof ResultSeries) {
            ResultSeries tv = (ResultSeries) object;
            Picasso.get()
                    .load(Links.IMAGE_PATH.concat(tv.getPosterPath()))
                    .placeholder(R.drawable.movies)
                    .into(poster);
            title.setText(tv.getOriginalName());
            overView.setText(tv.getOverview());
            date.setText(tv.getFirstAirDate());
            uri = Links.IMAGE_PATH.concat(tv.getPosterPath());
            return tv.getOriginalName();


        } else {
            String s;
            SearchingResults result = (SearchingResults) object;
            if ("tv".equals(result.getMediaType())) {
                Picasso.get()
                        .load(Links.IMAGE_PATH.concat(result.getPosterPath()))
                        .placeholder(R.drawable.movies)
                        .into(poster);
                title.setText(result.getOriginalName());
                overView.setText(result.getOverview());
                date.setText(result.getFirstAirDate());
                s = result.getOriginalName();
            } else {
                Picasso.get()
                        .load(Links.IMAGE_PATH.concat(result.getPosterPath()))
                        .placeholder(R.drawable.movies)
                        .into(poster);
                title.setText(result.getOriginalTitle());
                overView.setText(result.getOverview());
                date.setText(result.getReleaseDate());
                s = result.getOriginalTitle();
            }

            uri = Links.IMAGE_PATH.concat(result.getPosterPath());
            return s;

        }


    }

    private void voteAnimation(double vote) {
        Handler handler = new Handler() {

            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0) {
                    if (progress < vote) {
                        ++progress;
                        ringProgressBar.setProgress((int) progress);
                    }
                }

            }
        };
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void removeFromFavourites(String s) {
        Observable.just(s)
                .map(s1 -> {
                    DAO dao = DatabaseClient.getInstance(DetailsFragment.this.getContext()).getDataBase().getDAO();
                    List<FavouriteItem> items = dao.getAllFavourites();
                    for (FavouriteItem item : items){
                        if (item.title.equals(s1)){
                            dao.deleteFromFavourite(item);
                            break;
                        }
                    }
                    return "Removed from favourites";
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                        Toast.makeText(DetailsFragment.this.getContext(), s, Toast.LENGTH_SHORT).show();
                        favouriteBtn.setImageResource(R.drawable.favorite_border_24);
                        isFavourite = false;
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addToFavourites(FavouriteItem item) {
        Observable.just(item)
                .map(item1 -> {
                    DatabaseClient.getInstance(DetailsFragment.this.getContext()).getDataBase().getDAO().addFavourite(item1);
                    return "Added to favourites";
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                        Toast.makeText(DetailsFragment.this.getContext(), s, Toast.LENGTH_SHORT).show();
                        favouriteBtn.setImageResource(R.drawable.favorite_24);
                        isFavourite = true;
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void checkItemIfIsFavourite(String s) {
        Observable.just(s)
                .map(s1 -> DatabaseClient.getInstance(DetailsFragment.this.getContext())
                        .getDataBase()
                        .getDAO()
                        .getAllFavourites()).subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<List<FavouriteItem>>() {
                      @Override
                      public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                      }

                      @Override
                      public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<FavouriteItem> favouriteItems) {
                           for (int i = 0; i<favouriteItems.size(); i++){
                               if (favouriteItems.get(i).title .equals(s)){
                                   DetailsFragment.this.isFavourite = true;
                                   favouriteBtn.setImageResource(R.drawable.favorite_24);
                                   break;
                               }
                           }
                      }

                      @Override
                      public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                      }

                      @Override
                      public void onComplete() {

                      }
                  });
    }


}