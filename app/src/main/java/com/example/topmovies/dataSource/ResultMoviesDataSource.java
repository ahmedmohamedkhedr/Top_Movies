package com.example.topmovies.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.topmovies.models.PageMovies;
import com.example.topmovies.models.ResultMovies;
import com.example.topmovies.repo.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultMoviesDataSource extends PageKeyedDataSource<Integer, ResultMovies> {
    private static final int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ResultMovies> callback) {
        Call<PageMovies> moviesCall = Client.getInstance().getMoviesList(FIRST_PAGE);
        moviesCall.enqueue(new Callback<PageMovies>() {
            @Override
            public void onResponse(Call<PageMovies> call, Response<PageMovies> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<PageMovies> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResultMovies> callback) {
        Call<PageMovies> moviesCall = Client.getInstance().getMoviesList(params.key);
        moviesCall.enqueue(new Callback<PageMovies>() {
            @Override
            public void onResponse(Call<PageMovies> call, Response<PageMovies> response) {
                if (response.body() != null) {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(response.body().getResults(), key);
                }
            }

            @Override
            public void onFailure(Call<PageMovies> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResultMovies> callback) {
        Call<PageMovies> moviesCall = Client.getInstance().getMoviesList(params.key);
        moviesCall.enqueue(new Callback<PageMovies>() {
            @Override
            public void onResponse(Call<PageMovies> call, Response<PageMovies> response) {
                if (response.body() != null) {
                    Integer key = (response.body().getPage() < response.body().getTotalPages()) ? params.key + 1 : null;
                    callback.onResult(response.body().getResults(), key);

                }
            }

            @Override
            public void onFailure(Call<PageMovies> call, Throwable t) {
                  t.printStackTrace();
            }
        });
    }
}
