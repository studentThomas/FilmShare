package com.example.filmshare.logic;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private MovieShareApi api;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public interface SearchMoviesCallback {
        void onMoviesFound(List<Movie> movies);

        void onError(Throwable t);
    }


    public LiveData<List<Movie>> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public void deleteAllMovies() {
        movieRepository.deleteAllMovies();
    }

    public LiveData<Movie> getMovieById(int id) {
        return movieRepository.getMovieById(id);
    }

    public List<Movie> searchMovies(String query, SearchMoviesCallback callback) {
        List<Movie> movies = new ArrayList<>();

        Log.d("Movie", "searchMovies: " + query);
        String key = "b524ecf04a4dde849cafa595bf86982b";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieShareApi api = retrofit.create(MovieShareApi.class);

        Call<MovieResponse> call = api.searchMovies(key, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getMovies();
                    callback.onMoviesFound(movies);
                } else {
                    callback.onError(new Exception("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Handle network failure
                Log.d("Movie", "onFailure: " + t.getMessage());
                callback.onError(t);
            }
        });

        return movies;
    }


    public LiveData<List<Movie>> getMoviesByGenre(int genre) {
        MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();

        Log.d("Movie", "getMoviesByGenre: " + genre);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieShareApi service = retrofit.create(MovieShareApi.class);
        String key = "b524ecf04a4dde849cafa595bf86982b";

        Call<MovieResponse> call = service.getMoviesByGenre(key, genre);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> moviesGenre = response.body().getMovies();
                    for (Movie movie : moviesGenre) {
                        Log.d("Movie", "onResponse: " + movie.getTitle());
                    }
                    moviesLiveData.setValue(moviesGenre);
                } else {
                    Log.d("Movie", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Movie", "onFailure: " + t.getMessage());
            }
        });

        return moviesLiveData;
    }

    public LiveData<List<Movie>> sortMovies(String sort) {
        MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieShareApi service = retrofit.create(MovieShareApi.class);
        String key = "b524ecf04a4dde849cafa595bf86982b";

        Call<MovieResponse> call = service.sortMovies(key, sort);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> moviesGenre = response.body().getMovies();
                    for (Movie movie : moviesGenre) {
                        Log.d("Movie", "onResponse: " + movie.getTitle());
                    }
                    moviesLiveData.setValue(moviesGenre);
                } else {
                    Log.d("Movie", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Movie", "onFailure: " + t.getMessage());
            }
        });

        return moviesLiveData;
    }


}
