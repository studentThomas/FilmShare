package com.example.filmshare.domain;

import androidx.room.PrimaryKey;

public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    public Movie(String title) {
        this.title = title;
    }
}
