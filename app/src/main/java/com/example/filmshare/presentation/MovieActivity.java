package com.example.filmshare.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.filmshare.R;
import com.example.filmshare.datastorage.ListRepository;
import com.example.filmshare.logic.ListViewModel;
import com.example.filmshare.presentation.adapter.ListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AutoCompleteTextView autoCompleteTextViewLists;
    ArrayAdapter<String> arrayAdapterLists;

    private ListViewModel listViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_accent));
        }
        setContentView(R.layout.activity_movie);

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        ListRepository listRepository = new ListRepository(getApplication());

        listViewModel.getAllLists().observe(this, viewModelLists -> {
            List<String> listNames = new ArrayList<>();
            if (viewModelLists != null) {
                for (com.example.filmshare.domain.List list : viewModelLists) {
                    listNames.add(list.getName());
                }
            }
            //Drop down lists
            autoCompleteTextViewLists = findViewById(R.id.textview_lists);
            arrayAdapterLists = new ArrayAdapter<String>(this, R.layout.spinner_list_item, listNames);
            autoCompleteTextViewLists.setAdapter(arrayAdapterLists);
            autoCompleteTextViewLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String text = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                }
            });
            //Drop down lists
        });

        TextView title = findViewById(R.id.movie_title);
        TextView overview = findViewById(R.id.movie_overview);
        TextView language = findViewById(R.id.movie_language);
        ImageView backdrop = findViewById(R.id.movie_poster);
        TextView runtime = findViewById(R.id.movie_runtime);
        TextView budget = findViewById(R.id.movie_budget);
        TextView releaseDate = findViewById(R.id.movie_releaseDate);
        TextView revenue = findViewById(R.id.movie_revenue);
        TextView popularity = findViewById(R.id.movie_popularity);
        TextView status = findViewById(R.id.movie_status);
        TextView tagline = findViewById(R.id.movie_tagline);
        TextView voteAverage = findViewById(R.id.movie_voteAverage);
        TextView voteCount = findViewById(R.id.movie_voteCount);

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



        runtime.setText(getIntent().getStringExtra("runtime"));
        budget.setText(getIntent().getStringExtra("budget"));
        releaseDate.setText(getIntent().getStringExtra("releaseDate"));
        revenue.setText(getIntent().getStringExtra("revenue"));
        popularity.setText(getIntent().getStringExtra("popularity"));
        status.setText(getIntent().getStringExtra("status"));
        tagline.setText(getIntent().getStringExtra("tagline"));
        voteAverage.setText(getIntent().getStringExtra("voteAverage"));
        voteCount.setText(getIntent().getStringExtra("voteCount"));

    }
}