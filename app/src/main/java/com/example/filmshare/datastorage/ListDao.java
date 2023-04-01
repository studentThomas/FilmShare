package com.example.filmshare.datastorage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.filmshare.domain.List;


@Dao
public interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List list);

    @Delete
    void delete(List list);

    @Query("DELETE FROM list_table")
    void deleteAllLists();
//
    @Query("SELECT * FROM list_table ORDER BY id ASC")
    LiveData<java.util.List<List>> getAllLists();
}
