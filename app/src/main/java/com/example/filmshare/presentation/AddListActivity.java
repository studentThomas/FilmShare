package com.example.filmshare.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_accent));
        }
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