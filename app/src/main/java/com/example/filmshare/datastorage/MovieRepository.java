package com.example.filmshare.datastorage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.response.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MovieRepository {

    private MovieDao movieDao;

    public MovieRepository(Application application) {
        MovieShareDatabase database = MovieShareDatabase.getInstance(application);
        movieDao = database.movieDao();

    }


    public void deleteAllMovies()  {
        new deleteAllMoviesAsyncTask(movieDao).execute();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return movieDao.getAllMovies();
    }



    private static class deleteAllMoviesAsyncTask extends android.os.AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;

        private  deleteAllMoviesAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.deleteAllMovies();
            return null;
        }
    }
}
