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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filmshare.R;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.logic.MovieViewModel;
import com.example.filmshare.presentation.adapter.MovieAdapter;
import com.google.android.material.snackbar.Snackbar;



import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner_genre = findViewById(R.id.spinnerGenre);
        ArrayAdapter<CharSequence> adapter_genre = ArrayAdapter.createFromResource(this, R.array.Genre, android.R.layout.simple_spinner_item);
        adapter_genre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_genre.setAdapter(adapter_genre);
        spinner_genre.setOnItemSelectedListener(this);

        Spinner spinner_sorteren = findViewById(R.id.spinnerSorteren);
        ArrayAdapter<CharSequence> adapter_sorteren = ArrayAdapter.createFromResource(this, R.array.Sorteren, android.R.layout.simple_spinner_item);
        adapter_sorteren.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sorteren.setAdapter(adapter_sorteren);
        spinner_sorteren.setOnItemSelectedListener(this);


        Log.d("MovieShareDatabase", "onOpen: ");

        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        movieViewModel = viewModelProvider.get(MovieViewModel.class);

        //movieViewModel = new MovieViewModel(getApplication());

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

//                for (Movie movie : movies) {
//                    Log.d("Movie", "Print all movies");
//                    Log.d("Movie", movie.toString());
//                }

            }
        };

        movieViewModel.getAllMovies().observe(this, moviesObserver);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}