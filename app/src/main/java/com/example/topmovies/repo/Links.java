package com.example.topmovies.repo;

public interface Links {
    String BASE_URL = "https://api.themoviedb.org";
    String GET_MOVIES = "/3/trending/movie/day?api_key=e26d4bf428ee11683f0c840b7328e355";
    String GET_SERIES = "/3/trending/tv/day?api_key=e26d4bf428ee11683f0c840b7328e355";
    String SEARCH = "3/search/multi?api_key=e26d4bf428ee11683f0c840b7328e355";
    String IMAGE_PATH = "https://image.tmdb.org/t/p/w185";
}
