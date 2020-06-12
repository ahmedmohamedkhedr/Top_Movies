package com.example.topmovies.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.topmovies.models.PageSeries;
import com.example.topmovies.models.ResultSeries;
import com.example.topmovies.repo.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultSeriesDataSource extends PageKeyedDataSource<Integer, ResultSeries> {
    private static final int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ResultSeries> callback) {
        Call<PageSeries> seriesCall = Client.getInstance().getSeriesList(FIRST_PAGE);
        seriesCall.enqueue(new Callback<PageSeries>() {
            @Override
            public void onResponse(Call<PageSeries> call, Response<PageSeries> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<PageSeries> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResultSeries> callback) {
        Call<PageSeries> seriesCall = Client.getInstance().getSeriesList(params.key);
        seriesCall.enqueue(new Callback<PageSeries>() {
            @Override
            public void onResponse(Call<PageSeries> call, Response<PageSeries> response) {
                if (response.body() != null) {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(response.body().getResults(), key);
                }
            }

            @Override
            public void onFailure(Call<PageSeries> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResultSeries> callback) {
        Call<PageSeries> seriesCall = Client.getInstance().getSeriesList(params.key);
        seriesCall.enqueue(new Callback<PageSeries>() {
            @Override
            public void onResponse(Call<PageSeries> call, Response<PageSeries> response) {
                if (response.body() != null) {
                    Integer key = (response.body().getPage() < response.body().getTotalPages()) ? params.key + 1 : null;
                    callback.onResult(response.body().getResults(), key);
                }
            }

            @Override
            public void onFailure(Call<PageSeries> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
