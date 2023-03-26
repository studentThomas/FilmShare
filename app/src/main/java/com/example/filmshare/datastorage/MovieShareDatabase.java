package com.example.filmshare.datastorage;

import android.content.Context;

import androidx.room.Database;

import com.example.filmshare.domain.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieShareDatabase {

    private static MovieShareDatabase instance;
    public abstract MovieDao movieDao();

    public static synchronized MovieShareDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieShareDatabase.class, "movieshare_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

}
