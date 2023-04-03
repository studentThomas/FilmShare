package com.example.filmshare.datastorage;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.User;
import com.example.filmshare.domain.response.ListItemRequest;
import com.example.filmshare.domain.response.MovieResponse;
import com.example.filmshare.logic.SessionManager;
import com.example.filmshare.presentation.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListItemRepository {
    private ListItemDao listItemDao;



    public ListItemRepository(Application application) {
        MovieShareDatabase database = MovieShareDatabase.getInstance(application);
        listItemDao = database.listItemDao();
    }

    public void insert(ListItem listItem) {
        new insertListItemAsyncTask(listItemDao).execute(listItem);
    }

    public LiveData<List<ListItem>> getListItems(int listId) {
        return listItemDao.getListItems(listId);







//        String key = "b524ecf04a4dde849cafa595bf86982b";
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/3/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        MovieShareApi service = retrofit.create(MovieShareApi.class);


//        for (ListItem listItem : listItems.getValue()) {
//            Call<MovieResponse> call = service.getMovieById(listItem.getMovieId(), key);
//            call.enqueue(new Callback<MovieResponse>() {
//                @Override
//                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                    if (!response.isSuccessful()) {
//                        Log.d("ListRepository", "onResponse: " + response.code());
//                        MovieResponse result = response.body();
//                        List<Movie> movies = result.getMovies();
//                        listMovies.setValue(movies);
//                        for (Movie movie : movies) {
//                            Log.d("ListRepository", "onResponse: " + movie.getTitle());
//                        }
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<MovieResponse> call, Throwable t) {
//                    Log.d("ListRepository", "onFailure: " + t.getMessage());
//                }
//            });
//        }

    }



    private static class insertListItemAsyncTask extends android.os.AsyncTask<ListItem, Void, Void> {
        private ListItemDao listItemDao;

        private  insertListItemAsyncTask(ListItemDao listItemDao) {
            this.listItemDao = listItemDao;
        }

        @Override
        protected Void doInBackground(ListItem... listItems) {
            listItemDao.insert(listItems[0]);


            ListItem listItem = new ListItem(listItems[0].getListId(), listItems[0].getMovieId());

            Log.d("ListRepository", "doInBackground: " + listItems[0].getMovieId());

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

            ListItemRequest listItemRequest = new ListItemRequest(listItem.getMovieId());

            Call<ListItem> call = service.addListItem(listItem.getListId(), apiKey, sessionId, listItemRequest);

            call.enqueue(new Callback<ListItem>() {
                @Override
                public void onResponse(Call<ListItem> call, Response<ListItem> response) {
                    if (response.isSuccessful()) {
                        Log.d("ListRepository", "Success inserting listitem: " + response.body().toString());
                    } else {
                        Log.d("ListRepository", "Error inserting listitem: " + response.message());
                        Log.d("ListRepository", "Error inserting listitem: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ListItem> call, Throwable t) {
                    Log.d("ListRepository", "Error inserting list: " + t.getMessage());
                }

            });
            return null;
        }
    }




}
