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

    @Delete
    void delete(ListItem listItem);

    @Query("DELETE FROM list_item")
    void deleteAllListItems();
    //
    @Query("SELECT * FROM list_item ORDER BY listId DESC")
    LiveData<java.util.List<ListItem>> getListItems();
}
