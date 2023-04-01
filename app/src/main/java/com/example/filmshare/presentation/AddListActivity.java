package com.example.filmshare.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.filmshare.R;
import com.example.filmshare.domain.List;
import com.example.filmshare.logic.ListViewModel;

public class AddListActivity extends AppCompatActivity {

    private Button createListButton;
    private ListViewModel listViewModel;
    private EditText listName;
    private EditText listDescription;
    private EditText listLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        listViewModel = new ListViewModel(getApplication());


        listName = findViewById(R.id.list_name);
        listDescription = findViewById(R.id.list_description);
        listLanguage = findViewById(R.id.list_language);


        createListButton = findViewById(R.id.create_list);
        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewModel.insert(new List(listName.getText().toString(), listDescription.getText().toString(), listLanguage.getText().toString()));
                Intent intent = new Intent(AddListActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }
}