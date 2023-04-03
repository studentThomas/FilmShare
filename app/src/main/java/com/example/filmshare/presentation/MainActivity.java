package com.example.filmshare.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filmshare.R;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.logic.AuthViewModel;
import com.example.filmshare.logic.ListItemViewModel;
import com.example.filmshare.logic.MovieViewModel;
import com.example.filmshare.logic.SessionManager;
import com.example.filmshare.presentation.adapter.MovieAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MovieViewModel movieViewModel;
    private ListItemViewModel listItemViewModel;
    private SearchView searchView;
    private boolean isToastDisplayed = false;

    BottomNavigationView bottomNavigationView;

    AutoCompleteTextView autoCompleteTextViewGenre;
    AutoCompleteTextView autoCompleteTextViewSorteren;
    ArrayAdapter<String> arrayAdapterGenre;
    ArrayAdapter<String> arrayAdapterSorteren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_accent));
        }
        setContentView(R.layout.activity_main);

//        ListItemViewModel listItemViewModel = new ListItemViewModel(getApplication());
//
//        listItemViewModel.deleteAllListItems();
//        listItemViewModel.insertAllListItems(8247038	);

//        Log.d("MainActivity", "sessionId: " + SessionManager.getInstance().getSessionId());
//        Log.d("MainActivity", "userId: " + SessionManager.getInstance().getUserId());


        //Bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        searchView = findViewById(R.id.search);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!isToastDisplayed) {
                    Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();

                    Log.d("MainActivity", "userId: " + SessionManager.getInstance().getUserId());
                    isToastDisplayed = true;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                isToastDisplayed = false;
                return false;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        return true;
                    case R.id.action_lists:
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        //Drop down lists
        autoCompleteTextViewGenre = findViewById(R.id.textview_genre);
        arrayAdapterGenre = new ArrayAdapter<String>(this, R.layout.spinner_list_item, getResources().getStringArray(R.array.Genre));

        autoCompleteTextViewGenre.setAdapter(arrayAdapterGenre);

        autoCompleteTextViewSorteren = findViewById(R.id.textview_sort);
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
        //Drop down lists

        Log.d("MovieShareDatabase", "onOpen: ");

        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        movieViewModel = viewModelProvider.get(MovieViewModel.class);

        //movieViewModel = new MovieViewModel(getApplication());

        RecyclerView recyclerMovies = findViewById(R.id.recycler_view_movies);
        LinearLayoutManager layout_movies = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMovies.setLayoutManager(layout_movies);
        final MovieAdapter adapterMovie = new MovieAdapter();
        recyclerMovies.setAdapter(adapterMovie);
        recyclerMovies.setHasFixedSize(true);

        Observer<List<Movie>> moviesObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapterMovie.setMovies(movies);
            }
        };

//        Observer<List<ListItem>> listitemObserver = new Observer<List<ListItem>>() {
//            @Override
//            public void onChanged(@Nullable List<ListItem> listItems) {
//                List<Movie> movies = new ArrayList<>();
//
//                for(ListItem listItem : listItems) {
//                    int movieId = listItem.getMovieId();
//                    movieViewModel.getMovieById(movieId).observe(MainActivity.this, new Observer<Movie>() {
//                        @Override
//                        public void onChanged(@Nullable Movie movie) {
//                            if(movie != null) {
//                                Log.d("MainActivity", "movie: " + movie.getTitle());
//                                movies.add(movie);
//                                adapterMovie.setMovies(movies);
//                            }
//                        }
//                    });
//                }
//            }
//        };
//
//        listItemViewModel = new ListItemViewModel(getApplication());
//        listItemViewModel.getListItems(8245681).observe(this, listitemObserver);




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