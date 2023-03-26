package com.example.filmshare.datastorage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.*;
import androidx.room.OnConflictStrategy;

import com.example.filmshare.domain.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

//    @Query("DELETE FROM movie_table")
//    void deleteAll();
//
//    @Query("SELECT * FROM movie_table ORDER BY id DESC")
//    LiveData<List<Movie>> getAllMovies();


}
