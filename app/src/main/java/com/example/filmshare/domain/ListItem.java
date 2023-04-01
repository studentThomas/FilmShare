package com.example.filmshare.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_item", primaryKeys = {"listId", "movieId"})
public class ListItem {
    private int listId;
    private int movieId;

    public ListItem(int listId, int movieId) {
        this.listId = listId;
        this.movieId = movieId;
    }

    public int getListId() {
        return listId;
    }

    public int getMovieId() {
        return movieId;
    }

}
