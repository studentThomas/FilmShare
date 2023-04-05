package com.example.filmshare.datastorage;

import android.app.LauncherActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.filmshare.domain.Genre;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.response.GenreResponse;
import com.example.filmshare.domain.response.ListResponse;
import com.example.filmshare.domain.response.MovieResponse;
import com.example.filmshare.logic.SessionManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Database(entities = {Movie.class, ListItem.class, com.example.filmshare.domain.List.class, Genre.class}, version = 15)
public abstract class MovieShareDatabase extends RoomDatabase {

    private static MovieShareDatabase instance;
    public abstract MovieDao movieDao();
    public abstract ListDao listDao();
    public abstract ListItemDao listItemDao();
    public abstract GenreDao genreDao();






    public static synchronized MovieShareDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieShareDatabase.class, "movieshare_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateMovieAsyncTask(instance).execute();
            new PopulateGenreAsyncTask(instance).execute();
            new PopulateListAsyncTask(instance).execute();

        }
    };


    private static class PopulateMovieAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDao movieDao;

        private PopulateMovieAsyncTask(MovieShareDatabase db) {
            movieDao = db.movieDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllMovies();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieShareApi movieShareApi = retrofit.create(MovieShareApi.class);


            String key = "b524ecf04a4dde849cafa595bf86982b";

            Call<MovieResponse> call = movieShareApi.getMovies(key);

            int id = SessionManager.getInstance().getUserId();

            Log.d("MovieShareDatabase", "doInBackgroundid: " + id);

            try {
                Response<MovieResponse> response = call.execute();
                if (response.isSuccessful()) {
                    MovieResponse result = response.body();
                    List<Movie> movies = result.getMovies();
                    for (Movie movie : movies) {
                        Call<Movie> detailsCall = movieShareApi.getMovieDetails(movie.getId(), key);
                        Response<Movie> detailsResponse = detailsCall.execute();
                        Movie details = detailsResponse.body();
                        movieDao.insert(details);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        }


    private static class PopulateGenreAsyncTask extends AsyncTask<Void, Void, Void> {

        private GenreDao genreDao;

        private PopulateGenreAsyncTask(MovieShareDatabase db) {
            genreDao = db.genreDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            genreDao.deleteAllGenres();
            Log.d("MovieShareDatabase", "doInBackground: " + "genre");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieShareApi service = retrofit.create(MovieShareApi.class);
            String apiKey = "b524ecf04a4dde849cafa595bf86982b";

            Call<GenreResponse> call = service.getAllGenres(apiKey);


            try {
                Response<GenreResponse> response = call.execute();
                if (response.isSuccessful()) {
                    GenreResponse result = response.body();
                    List<Genre> genres = result.getGenres();
                    for (Genre genre : genres) {
                        genreDao.insert(genre);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }



    }

    private static class PopulateListAsyncTask extends AsyncTask<Void, Void, Void> {

        private ListDao listDao;
        private ListItemDao listItemDao;

        private PopulateListAsyncTask(MovieShareDatabase db) {
            listDao = db.listDao();
            listItemDao = db.listItemDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            listDao.deleteAllLists();
            listItemDao.deleteAllListItems();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieShareApi movieShareApi = retrofit.create(MovieShareApi.class);


            String key = "b524ecf04a4dde849cafa595bf86982b";
            int userId = SessionManager.getInstance().getUserId();
            String sessionId = SessionManager.getInstance().getSessionId();

            Log.d("MovieShareDatabase", "doInBackground: reading lists");

            Call<ListResponse> call = movieShareApi.getLists(userId, key, sessionId);

            try {
                Response<ListResponse> response = call.execute();
                if (response.isSuccessful()) {
                    ListResponse result = response.body();
                    List<com.example.filmshare.domain.List> lists = result.getLists();
                    for (com.example.filmshare.domain.List list : lists) {
//                        Log.d("MovieShareDatabase", "doInBackground: " + list.getName());



                        int listId = list.getId();
                        Call<MovieResponse> call2 = movieShareApi.getListItems(listId, key);
                        try {
                            Response<MovieResponse> response2 = call2.execute();
                            if (response.isSuccessful()) {
                                MovieResponse result2 = response2.body();
                                List<Movie> movies = result2.getListItems();
                                for (Movie movie : movies) {
//                                    Log.d("ListRepository", "doInBackground: getting list items " + movie.getTitle());
                                    ListItem listItem = new ListItem(listId, movie.getId());
                                    listItemDao.insert(listItem);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        listDao.insert(list);
                    }
                } else {
                    Log.d("MovieShareDatabase", "doInBackground: " + response.errorBody().string());
                    Log.d("MovieShareDatabase", "error:" + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }




    }





}
