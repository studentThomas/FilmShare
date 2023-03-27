package com.example.filmshare.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.filmshare.R;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.logic.MovieViewModel;
import com.example.filmshare.presentation.adapter.MovieAdapter;
import com.google.android.material.snackbar.Snackbar;



import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("MovieShareDatabase", "onOpen: ");

        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        movieViewModel = viewModelProvider.get(MovieViewModel.class);

//        movieViewModel = new MovieViewModel(getApplication());

        RecyclerView recyclerMovies = findViewById(R.id.recycler_view_movies);
        LinearLayoutManager layout_meals = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMovies.setLayoutManager(layout_meals);
        final MovieAdapter adapterMeals = new MovieAdapter();
        recyclerMovies.setAdapter(adapterMeals);
        recyclerMovies.setHasFixedSize(true);



        Observer<List<Movie>> moviesObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapterMeals.setMovies(movies);
                Snackbar.make(recyclerMovies, String.valueOf(movies.size() + " Movies read"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                for (Movie movie : movies) {
                    Log.d("Movie", "Print all movies");
                    Log.d("Movie", movie.toString());
                }

            }
        };

        movieViewModel.getAllMovies().observe(this, moviesObserver);
    }
}