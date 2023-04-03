package com.example.filmshare.logic;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.filmshare.datastorage.ListItemRepository;
import com.example.filmshare.datastorage.ListRepository;
import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListItemViewModel extends AndroidViewModel {
    private ListItemRepository listItemRepository;
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> movies;
    private LiveData<List<ListItem>> listItems;

    public ListItemViewModel(@NonNull Application application) {
        super(application);
        listItemRepository = new ListItemRepository(application);
        movieRepository = new MovieRepository(application);
    }


    public void insert(ListItem listItem) {
        listItemRepository.insert(listItem);
    }

    public LiveData<java.util.List<ListItem>> getListItems(int listId) {
        return  listItemRepository.getListItems(listId);



    }



}
