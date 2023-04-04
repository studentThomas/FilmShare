package com.example.filmshare.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                if(listItems.isEmpty()) {
                    Log.d("ListItemsActivity", "onCreate: listItems is empty");
                    findViewById(R.id.textinput_genre).setVisibility(View.INVISIBLE);
                    findViewById(R.id.textinput_sort).setVisibility(View.INVISIBLE);
                    Toast.makeText(ListItemsActiviy.this, "List is empty", Toast.LENGTH_LONG).show();

                    return;
                } else {
                    Log.d("ListItemsActivity", "onCreate: listItems is not empty");
                }
                for (ListItem listItem : listItems) {
                    int movieId = listItem.getMovieId();
                    listId = listItem.getListId();
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

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |  ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Movie listItem = adapterMovie.getItem(position);
                int movieId = listItem.getId();
                listItemViewModel.delete(movieId, listId);
                Log.d("ListItemsActivity", "onSwiped: " + movieId + " " + listId);
                adapterMovie.removeItem(position);
            }
        });

        helper.attachToRecyclerView(recyclerMovies);




        listItemViewModel = new ListItemViewModel(getApplication());
        listItemViewModel.getListItems(listId).observe(this, listitemObserver);
        movieViewModel = new MovieViewModel(getApplication());




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, check out this list on FilmShare: " + "https://www.themoviedb.org/list/" + listId);
        startActivity(Intent.createChooser(intent, "Share"));
        return super.onOptionsItemSelected(item);
    }
}