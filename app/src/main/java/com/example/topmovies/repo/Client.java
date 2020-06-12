package com.example.topmovies.repo;

import com.example.topmovies.models.PageMovies;
import com.example.topmovies.models.PageSearching;
import com.example.topmovies.models.PageSeries;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client client;

    private Client() {

    }

    public static synchronized Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    private Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Links.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public Call<PageMovies> getMoviesList(int id){
        Call<PageMovies> moviesCall;
        ApiRequest apiRequest = getClient().create(ApiRequest.class);
        moviesCall = apiRequest.getMovies(id);
        return moviesCall;
    }

    public Call<PageSeries> getSeriesList(int id){
        Call<PageSeries> seriesCall;
        ApiRequest apiRequest = getClient().create(ApiRequest.class);
        seriesCall = apiRequest.getSeries(id);
        return seriesCall;
    }

    public Call<PageSearching> searchResults(String query){
        Call<PageSearching> call ;
        ApiRequest apiRequest = getClient().create(ApiRequest.class);
        call = apiRequest.search(query);
        return call;
    }

}
