package com.example.filmshare.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.filmshare.R;
import com.example.filmshare.domain.List;
import com.example.filmshare.logic.ListViewModel;

public class AddListActivity extends AppCompatActivity {

    private Button createListButton;
    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        listViewModel = new ListViewModel(getApplication());



        createListButton = findViewById(R.id.create_list);
        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewModel.insert(new List("test", "description test", "en"));
            }
        });
    }
}