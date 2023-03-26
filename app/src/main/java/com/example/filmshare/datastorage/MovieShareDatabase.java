package com.example.filmshare.datastorage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.filmshare.domain.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieShareDatabase extends RoomDatabase {

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

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateMovieAsyncTask(instance).execute();
        }
    };


    private static class PopulateMovieAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDao movieDao;

        private PopulateMovieAsyncTask(MovieShareDatabase db) {
            movieDao = db.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.insert(new Movie("Movie 1"));
            movieDao.insert(new Movie("Movie 2"));
            movieDao.insert(new Movie("Movie 3"));
            Log.d("MovieShareDatabase", "onOpen: ");

            return null;
        }
    }

}
