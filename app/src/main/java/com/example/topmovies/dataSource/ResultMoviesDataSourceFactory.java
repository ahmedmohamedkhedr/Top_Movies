package com.example.topmovies.dataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.topmovies.models.ResultMovies;

public class ResultMoviesDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer , ResultMovies>> dataSourceMutableLiveData = new MutableLiveData<>();
    @Override
    public DataSource create() {
        ResultMoviesDataSource dataSource = new ResultMoviesDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public LiveData<PageKeyedDataSource<Integer, ResultMovies>> getDataSourceLiveData() {
        return dataSourceMutableLiveData;
    }

}
