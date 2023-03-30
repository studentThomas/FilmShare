package com.example.filmshare.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.filmshare.R;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        TextView title = findViewById(R.id.movie_title);
        TextView overview = findViewById(R.id.movie_overview);
        TextView language = findViewById(R.id.movie_language);

        title.setText(getIntent().getStringExtra("title"));
        overview.setText(getIntent().getStringExtra("overview"));
        language.setText(getIntent().getStringExtra("language"));
    }
}