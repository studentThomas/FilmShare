package com.example.filmshare.datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.filmshare.domain.Genre;
import com.example.filmshare.domain.Movie;

import java.util.List;

public class GenreRepository {
    private GenreDao genreDao;


    public GenreRepository(Application application) {
        MovieShareDatabase database = MovieShareDatabase.getInstance(application);
        genreDao = database.genreDao();

    }



    public void deleteAllGenres()  {
        new GenreRepository.deleteAllGenresAsyncTask(genreDao).execute();
    }

    public LiveData<List<Genre>> getAllGenres() {
        return genreDao.getAllGenres();

    }



    private static class deleteAllGenresAsyncTask extends android.os.AsyncTask<Genre, Void, Void> {
        private GenreDao genreDao;

        private  deleteAllGenresAsyncTask(GenreDao genreDao) {
            this.genreDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre...genres) {
            genreDao.deleteAllGenres();
            return null;
        }
    }
}
