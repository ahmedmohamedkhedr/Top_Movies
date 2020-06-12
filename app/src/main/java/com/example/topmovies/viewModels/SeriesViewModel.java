package com.example.topmovies.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import com.example.topmovies.dataSource.ResultSeriesDataSourceFactory;
import com.example.topmovies.models.ResultSeries;

public class SeriesViewModel extends ViewModel {
    public LiveData<PagedList<ResultSeries>> pagedListLiveData;
    public LiveData<PageKeyedDataSource<Integer, ResultSeries>> pageKeyedDataSourceLiveData;

    public SeriesViewModel() {
        ResultSeriesDataSourceFactory sourceFactory = new ResultSeriesDataSourceFactory();
        pageKeyedDataSourceLiveData = sourceFactory.getDataSourceLiveData();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();
        pagedListLiveData = (new LivePagedListBuilder(sourceFactory, config)).build();

    }
}
