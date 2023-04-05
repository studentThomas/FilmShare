package com.example.filmshare.logic;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.filmshare.datastorage.GenreRepository;
import com.example.filmshare.domain.Genre;
import com.example.filmshare.domain.Review;

import java.util.List;

public class GenreViewModel extends AndroidViewModel {
    private GenreRepository genreRepository;
    private LiveData<List<Genre>> allGenres;

    public GenreViewModel(@NonNull Application application) {
        super(application);
        genreRepository = new GenreRepository(application);
        allGenres = genreRepository.getAllGenres();
    }


    public LiveData<List<Genre>> getAllGenres() {
        return allGenres;
    }
}
