package com.example.topmovies.repo;

import com.example.topmovies.models.PageMovies;
import com.example.topmovies.models.PageSearching;
import com.example.topmovies.models.PageSeries;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET(Links.GET_SERIES)
    Call<PageSeries> getSeries(@Query("page") int page);

    @GET(Links.GET_MOVIES)
    Call<PageMovies> getMovies(@Query("page") int page);

    @GET(Links.SEARCH)
    Call<PageSearching> search(@Query("query") String query);


}
