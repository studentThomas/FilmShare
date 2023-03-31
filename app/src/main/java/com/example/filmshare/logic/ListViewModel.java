package com.example.filmshare.logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.datastorage.MovieShareApi;

public class ListViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }


}
