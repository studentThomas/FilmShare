package com.example.filmshare.logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.filmshare.datastorage.ListRepository;
import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.List;


public class ListViewModel extends AndroidViewModel {
    private ListRepository listRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        listRepository = new ListRepository(application);
    }


    public void insert(List list) {
        listRepository.insert(list);
    }

//    public void deleteAllLists() {
//        listRepository.deleteAllLists();
//    }
//
    public LiveData<java.util.List<List>> getAllLists() {
        return listRepository.getAllLists();
    }

}
