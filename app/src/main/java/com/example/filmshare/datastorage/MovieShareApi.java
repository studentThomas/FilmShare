package com.example.filmshare.datastorage;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

import com.example.filmshare.domain.RequestToken;
import com.example.filmshare.domain.User;
import com.example.filmshare.domain.response.MovieResponse;

public interface MovieShareApi {

    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String key);

    @GET("authentication/token/new")
    Call<RequestToken> getRequestToken(@Query("api_key") String key);
}
