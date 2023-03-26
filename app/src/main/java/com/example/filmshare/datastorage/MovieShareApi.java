package com.example.filmshare.datastorage;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

import com.example.filmshare.domain.response.MovieResponse;

public interface MovieShareApi {

    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String key);
}
