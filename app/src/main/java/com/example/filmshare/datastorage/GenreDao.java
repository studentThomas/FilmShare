package com.example.filmshare.datastorage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.filmshare.domain.Genre;

import java.util.List;

@Dao
public interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Genre genre);

    @Query("SELECT * FROM genre_table")
    LiveData<List<Genre>> getAllGenres();

    @Query("DELETE FROM genre_table")
    void deleteAllGenres();
}
