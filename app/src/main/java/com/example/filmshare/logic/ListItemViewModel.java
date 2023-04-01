package com.example.filmshare.logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.filmshare.datastorage.ListItemRepository;
import com.example.filmshare.datastorage.ListRepository;
import com.example.filmshare.domain.List;
import com.example.filmshare.domain.ListItem;

public class ListItemViewModel extends AndroidViewModel {
    private ListItemRepository listItemRepository;

    public ListItemViewModel(@NonNull Application application) {
        super(application);
        listItemRepository = new ListItemRepository(application);
    }


    public void insert(ListItem listItem) {
        listItemRepository.insert(listItem);
    }

    public LiveData<java.util.List<ListItem>> getListItems() {
        return listItemRepository.getListItems();
    }







}
