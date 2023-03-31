package com.example.filmshare.datastorage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.filmshare.domain.List;
import com.example.filmshare.logic.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListRepository {
    private ListDao listDao;


    public ListRepository(Application application) {
        MovieShareDatabase database = MovieShareDatabase.getInstance(application);
        listDao = database.listDao();
    }

    public void insert(List list) {
        new insertListAsyncTask(listDao).execute(list);
    }

//    public void deleteAllLists()  {
//        new deleteAllListsAsyncTask(listDao).execute();
//    }

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
//            listDao.insert(lists[0]);


            List list = new List(lists[0].getName(), lists[0].getDescription(), lists[0].getLanguage());

            Log.d("ListRepository", "doInBackground: " + lists[0].getName());

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            String apiKey = "b524ecf04a4dde849cafa595bf86982b";
            String sessionId = SessionManager.getInstance().getSessionId();
            MovieShareApi service = retrofit.create(MovieShareApi.class);

            Call<List> call = service.createList(sessionId, apiKey, list);

            call.enqueue(new Callback<List>() {
                @Override
                public void onResponse(Call<List> call, Response<List> response) {
                    if (response.isSuccessful()) {
                        List listResponse = response.body();
                    } else {
                        Log.d("ListRepository", "Error inserting list: " + response.message());
                        Log.d("ListRepository", "Error inserting list: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List> call, Throwable t) {
                    Log.d("ListRepository", "Error inserting list: " + t.getMessage());
                }

            });




            return null;
        }
    }

//    private static class deleteAllListsAsyncTask extends android.os.AsyncTask<List, Void, Void> {
//        private ListDao listDao;
//
//        private  deleteAllListsAsyncTask(ListDao listDao) {
//            this.listDao = listDao;
//        }
//
//        @Override
//        protected Void doInBackground(List... lists) {
//            listDao.deleteAllLists();
//            return null;
//        }
//    }



}
