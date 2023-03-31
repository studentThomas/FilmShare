package com.example.filmshare.presentation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmshare.R;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.logic.ListViewModel;
import com.example.filmshare.logic.SessionManager;
import com.example.filmshare.presentation.adapter.ListAdapter;
import com.example.filmshare.presentation.adapter.MovieAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button addListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_accent));
        }
        setContentView(R.layout.activity_list);

        addListButton = findViewById(R.id.add_list);

        //Bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.action_lists);

        ListViewModel listViewModel = new ListViewModel(getApplication());


        RecyclerView recyclerLists = findViewById(R.id.recycler_view_lists);
        LinearLayoutManager layout_lists = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerLists.setLayoutManager(layout_lists);
        final ListAdapter adapterLists = new ListAdapter();
        recyclerLists.setAdapter(adapterLists);
        recyclerLists.setHasFixedSize(true);

        Observer<List<com.example.filmshare.domain.List>> listObserver = new Observer<List<com.example.filmshare.domain.List>>() {
            @Override
            public void onChanged(@Nullable List<com.example.filmshare.domain.List> lists) {
                adapterLists.setLists(lists);
//                Snackbar.make(recyclerMovies, String.valueOf(movies.size() + " Movies read"), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                for (Movie movie : movies) {
//                    Log.d("Movie", "Print all movies");
//                    Log.d("Movie", movie.toString());
//                }

            }
        };

        Log.d("MainActivity", "sessionId: " + SessionManager.getInstance().getSessionId());
        Log.d("MainActivity", "userId: " + SessionManager.getInstance().getUserId());

        listViewModel.getAllLists().observe(this, listObserver);


        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, AddListActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_lists:
                        return true;
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
    }
}