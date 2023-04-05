package com.example.filmshare.datastorage;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.filmshare.domain.List;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.User;
import com.example.filmshare.domain.response.GenreResponse;
import com.example.filmshare.domain.response.ListItemRequest;
import com.example.filmshare.domain.response.ListResponse;

import com.example.filmshare.domain.response.ReviewResponse;
import com.example.filmshare.domain.response.SessionRequest;
import com.example.filmshare.domain.response.SessionResponse;
import com.example.filmshare.domain.response.TokenResponse;
import com.example.filmshare.domain.response.MovieResponse;

public interface MovieShareApi {

    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String key);

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByGenre(@Query("api_key") String apiKey, @Query("with_genres") int genreId);

    @GET("discover/movie")
    Call<MovieResponse> sortMovies(@Query("api_key") String apiKey, @Query("sort_by") String sortBy);

    @GET("genre/movie/list")
    Call<GenreResponse> getAllGenres(@Query("api_key") String apiKey);


    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("api_key") String apiKey, @Query("query") String query);

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

    @POST("list/{list_id}/add_item")
    Call<ListItem> addListItem(@Path("list_id") int listId, @Query("api_key") String apiKey, @Query("session_id") String sessionId, @Body ListItemRequest requestBody);

    @POST("list/{list_id}/remove_item")
    Call<ListItem> removeMovieFromList(@Path("list_id") int listId, @Query("api_key") String apiKey, @Query("session_id") String sessionId, @Body ListItemRequest requestBody);

//    @POST("lists/{list_id}/remove_item")
//    Call<ListItem> removeMovieFromList(@Path("list_id") int listId, @Query("api_key") String apiKey, @Query("session_id") String sessionId, @Body ListItemRequest requestBody);

    @GET("list/{list_id}")
    Call<MovieResponse> getListItems(@Path("list_id") int listId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey
    );

}
