package com.example.filmshare.datastorage;

import retrofit2.http.GET;
import retrofit2.Call;
import com.example.filmshare.domain.response.MovieResponse;

public interface MovieShareApi {

    @GET("movie")
    Call<MovieResponse> getMovies();
}
