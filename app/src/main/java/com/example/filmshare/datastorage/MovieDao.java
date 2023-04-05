package com.example.filmshare.datastorage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.*;

import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.response.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Dao
public interface MovieDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("DELETE FROM movie_table")
    void deleteAllMovies();

    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    LiveData<Movie> getMovieById(int movieId);





}
