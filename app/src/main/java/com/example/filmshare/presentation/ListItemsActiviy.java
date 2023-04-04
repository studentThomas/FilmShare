package com.example.filmshare.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.filmshare.R;
import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.response.MovieResponse;
import com.example.filmshare.logic.ListItemViewModel;
import com.example.filmshare.logic.MovieViewModel;
import com.example.filmshare.logic.SessionManager;
import com.example.filmshare.presentation.adapter.MovieAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListItemsActiviy extends AppCompatActivity {

    private MovieViewModel movieViewModel;
    private ListItemViewModel listItemViewModel;

    private int listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_accent));
        }
        setContentView(R.layout.activity_list_items_activiy);


//        ListItemViewModel listItemViewModel = new ListItemViewModel(getApplication());
//
//        listItemViewModel.insertAllListItems(8245681);


        listId = getIntent().getIntExtra("id", 0);


        RecyclerView recyclerMovies = findViewById(R.id.recycler_view_movies);
        LinearLayoutManager layout_movies = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMovies.setLayoutManager(layout_movies);
        final MovieAdapter adapterMovie = new MovieAdapter();
        recyclerMovies.setAdapter(adapterMovie);
        recyclerMovies.setHasFixedSize(true);


        String key = "b524ecf04a4dde849cafa595bf86982b";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieShareApi movieShareApi = retrofit.create(MovieShareApi.class);

        Observer<List<ListItem>> listitemObserver = new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> listItems) {
                List<Movie> movies = new ArrayList<>();
                for (ListItem listItem : listItems) {
                    int movieId = listItem.getMovieId();
                    // Fetch the movie using the API
                    Call<Movie> detailsCall = movieShareApi.getMovieDetails(movieId, key);
                    detailsCall.enqueue(new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                            if (response.isSuccessful()) {
                                Movie details = response.body();
                                movies.add(details);
                                adapterMovie.setMovies(movies);
                            } else {
                                Log.d("MainActivity", "Error: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {
                            // Handle error
                        }
                    });
                }
            }
        };


        listItemViewModel = new ListItemViewModel(getApplication());
        listItemViewModel.getListItems(listId).observe(this, listitemObserver);
        movieViewModel = new MovieViewModel(getApplication());




    }
}