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
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.logic.ListItemViewModel;
import com.example.filmshare.logic.MovieViewModel;
import com.example.filmshare.presentation.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

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


        Observer<List<ListItem>> listitemObserver = new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> listItems) {
                List<Movie> movies = new ArrayList<>();

                for(ListItem listItem : listItems) {
                    int movieId = listItem.getMovieId();
                    movieViewModel.getMovieById(movieId).observe(ListItemsActiviy.this, new Observer<Movie>() {
                        @Override
                        public void onChanged(@Nullable Movie movie) {
                            if(movie != null) {
                                Log.d("MainActivity", "movie: " + movie.getTitle());
                                movies.add(movie);
                                adapterMovie.setMovies(movies);
                            }
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