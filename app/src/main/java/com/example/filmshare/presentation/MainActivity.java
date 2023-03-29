package com.example.filmshare.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filmshare.R;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.logic.MovieViewModel;
import com.example.filmshare.presentation.adapter.MovieAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MovieViewModel movieViewModel;

    AutoCompleteTextView autoCompleteTextViewGenre;
    AutoCompleteTextView autoCompleteTextViewSorteren;
    ArrayAdapter<String> arrayAdapterGenre;
    ArrayAdapter<String> arrayAdapterSorteren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextViewGenre = findViewById(R.id.textview_genre);
        arrayAdapterGenre = new ArrayAdapter<String>(this, R.layout.spinner_list_item, getResources().getStringArray(R.array.Genre));

        autoCompleteTextViewGenre.setAdapter(arrayAdapterGenre);

        autoCompleteTextViewSorteren = findViewById(R.id.textview_sorteren);
        arrayAdapterSorteren = new ArrayAdapter<String>(this, R.layout.spinner_list_item, getResources().getStringArray(R.array.Sorteren));

        autoCompleteTextViewSorteren.setAdapter(arrayAdapterSorteren);
        autoCompleteTextViewGenre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextViewSorteren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });


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