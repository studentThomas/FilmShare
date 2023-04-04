package com.example.filmshare.datastorage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.filmshare.domain.List;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;

@Dao
public interface ListItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ListItem listItem);



    @Query("DELETE FROM list_item WHERE movieId = :id")
    void delete(int id);



    @Query("DELETE FROM list_item")
    void deleteAllListItems();
    //
    @Query("SELECT * FROM list_item WHERE listId = :listId ORDER BY listId ASC")
    LiveData<java.util.List<ListItem>> getListItems(int listId);
}
