package com.example.topmovies.dataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.topmovies.models.ResultSeries;

public class ResultSeriesDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, ResultSeries>> dataSourceMutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource create() {
        ResultSeriesDataSource dataSource = new ResultSeriesDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public LiveData<PageKeyedDataSource<Integer, ResultSeries>> getDataSourceLiveData() {
        return dataSourceMutableLiveData;
    }
}
