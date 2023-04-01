package com.example.filmshare.datastorage;

import android.app.Application;
import android.util.Log;

import com.example.filmshare.domain.List;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.response.ListItemRequest;
import com.example.filmshare.logic.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
