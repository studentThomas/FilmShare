package com.example.filmshare.datastorage;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.filmshare.domain.List;
import com.example.filmshare.domain.User;
import com.example.filmshare.domain.response.ListResponse;
import com.example.filmshare.domain.response.SessionRequest;
import com.example.filmshare.domain.response.SessionResponse;
import com.example.filmshare.domain.response.TokenResponse;
import com.example.filmshare.domain.response.MovieResponse;

public interface MovieShareApi {

    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String key);

    @GET("authentication/token/new")
    Call<TokenResponse> getRequestToken(@Query("api_key") String key);

    @GET("account")
    Call<User> getAccountDetails(@Query("session_id") String sessionId, @Query("api_key") String apiKey);

    @POST("authentication/session/new")
    Call<SessionResponse> createSession(@Query("api_key") String apiKey, @Body SessionRequest requestBody);

    @POST("list")
    Call<List> createList(@Query("session_id") String sessionId, @Query("api_key") String apiKey, @Body List requestBody);

    @GET("account/{account_id}/lists")
    Call<ListResponse> getLists(@Path("account_id") int accountId, @Query("api_key") String apiKey, @Query("session_id") String sessionId);


}
