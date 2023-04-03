package com.example.filmshare.logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.domain.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
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

}
