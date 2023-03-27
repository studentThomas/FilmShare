package com.example.filmshare.datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.User;

import java.util.List;

public class UserRepository {

    private UserDao userDao;

    public UserRepository(Application application) {
        MovieShareDatabase database = MovieShareDatabase.getInstance(application);
        userDao = database.userDao();

    }






    public void insert(User user)  {
        new insertUserAsyncTask(userDao).execute(user);
    }


    private static class insertUserAsyncTask extends android.os.AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private  insertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
}
