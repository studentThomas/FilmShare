package com.example.filmshare.datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.filmshare.domain.List;

public class ListRepository {
    private ListDao listDao;


    public ListRepository(Application application) {
        MovieShareDatabase database = MovieShareDatabase.getInstance(application);
        listDao = database.listDao();
    }

    public void insert(List list) {
        new insertListAsyncTask(listDao).execute(list);
    }

    public void deleteAllLists()  {
        new deleteAllListsAsyncTask(listDao).execute();
    }

    public LiveData<java.util.List<List>> getAllLists() {
        return listDao.getAllLists();
    }

    private static class insertListAsyncTask extends android.os.AsyncTask<List, Void, Void> {
        private ListDao listDao;

        private  insertListAsyncTask(ListDao listDao) {
            this.listDao = listDao;
        }

        @Override
        protected Void doInBackground(List... lists) {
            listDao.insert(lists[0]);
            return null;
        }
    }

    private static class deleteAllListsAsyncTask extends android.os.AsyncTask<List, Void, Void> {
        private ListDao listDao;

        private  deleteAllListsAsyncTask(ListDao listDao) {
            this.listDao = listDao;
        }

        @Override
        protected Void doInBackground(List... lists) {
            listDao.deleteAllLists();
            return null;
        }
    }



}
