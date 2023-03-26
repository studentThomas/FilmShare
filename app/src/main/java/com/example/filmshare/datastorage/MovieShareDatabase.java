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
import com.example.filmshare.domain.response.MovieResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            movieDao.deleteAllMovies();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieShareApi movieShareApi = retrofit.create(MovieShareApi.class);



            String key = "b524ecf04a4dde849cafa595bf86982b";


            Call<MovieResponse> call = movieShareApi.getMovies(key);

            try {
                Response<MovieResponse> response = call.execute();
                if (response.isSuccessful()) {
                    MovieResponse result = response.body();
                    List<Movie> movies = result.getMovies();
                    for (Movie movie : movies) {
                        movieDao.insert(movie);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }






        }


}
