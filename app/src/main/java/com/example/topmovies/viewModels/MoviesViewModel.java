package com.example.topmovies.viewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.topmovies.dataSource.ResultMoviesDataSourceFactory;
import com.example.topmovies.models.ResultMovies;


public class MoviesViewModel extends ViewModel {
    public LiveData<PagedList<ResultMovies>> pagedListLiveData;
    public LiveData<PageKeyedDataSource<Integer, ResultMovies>> pageKeyedDataSourceLiveData;

    public MoviesViewModel() {
        ResultMoviesDataSourceFactory sourceFactory = new ResultMoviesDataSourceFactory();
        pageKeyedDataSourceLiveData = sourceFactory.getDataSourceLiveData();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();
        pagedListLiveData = (new LivePagedListBuilder(sourceFactory, config)).build();

    }
}
