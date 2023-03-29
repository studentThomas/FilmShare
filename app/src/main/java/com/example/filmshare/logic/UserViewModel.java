package com.example.filmshare.logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.datastorage.UserRepository;
import com.example.filmshare.domain.User;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }


    public void insert(User user) {
        userRepository.insert(user);
    }



}
