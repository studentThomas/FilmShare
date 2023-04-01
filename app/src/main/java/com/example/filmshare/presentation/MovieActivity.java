package com.example.filmshare.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filmshare.R;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.logic.ListItemViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieActivity extends AppCompatActivity {

    private Button addMovieButton;
    private ListItemViewModel listItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        addMovieButton = findViewById(R.id.add_movie);
        listItemViewModel = new ListItemViewModel(getApplication());

        TextView title = findViewById(R.id.movie_title);
        TextView overview = findViewById(R.id.movie_overview);
        TextView language = findViewById(R.id.movie_language);
        ImageView backdrop = findViewById(R.id.movie_poster);
//        TextView runtime = findViewById(R.id.movie_runtime);
//        TextView budget = findViewById(R.id.movie_budget);
//        TextView releaseDate = findViewById(R.id.movie_releaseDate);
//        TextView revenue = findViewById(R.id.movie_revenue);
//        TextView popularity = findViewById(R.id.movie_popularity);
//        TextView status = findViewById(R.id.movie_status);
//        TextView tagline = findViewById(R.id.movie_tagline);
//        TextView voteAverage = findViewById(R.id.movie_voteAverage);
//        TextView voteCount = findViewById(R.id.movie_voteCount);

        title.setText(getIntent().getStringExtra("title"));
        overview.setText(getIntent().getStringExtra("overview"));
        language.setText(getIntent().getStringExtra("language"));


//        Glide.with(this).load(getIntent().getStringExtra("backdrop")).into(backdrop);

        String imageUrl = "https://image.tmdb.org/t/p/w500" + getIntent().getStringExtra("backdrop");

        Glide.with(backdrop.getContext())
                .load(imageUrl)
                .into(backdrop);

//        Glide.with(movieViewHolder.backdrop.getContext())
//                .load(imageUrl)
//                .into(movieViewHolder.backdrop);



//        runtime.setText(getIntent().getStringExtra("runtime"));
//        budget.setText(getIntent().getStringExtra("budget"));
//        releaseDate.setText(getIntent().getStringExtra("releaseDate"));
//        revenue.setText(getIntent().getStringExtra("revenue"));
//        popularity.setText(getIntent().getStringExtra("popularity"));
//        status.setText(getIntent().getStringExtra("status"));
//        tagline.setText(getIntent().getStringExtra("tagline"));
//        voteAverage.setText(getIntent().getStringExtra("voteAverage"));
//        voteCount.setText(getIntent().getStringExtra("voteCount"));




        addMovieButton.setOnClickListener(v -> {
            int movieId = getIntent().getIntExtra("id", 0);
            listItemViewModel.insert(new ListItem(8245681, movieId));
            Log.d("MovieActivity", "Movie added to list: " + movieId);
        });

    }
}