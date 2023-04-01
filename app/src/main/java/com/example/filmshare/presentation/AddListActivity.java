package com.example.filmshare.presentation;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.os.Build;
=======
import android.content.Intent;
>>>>>>> 820071052415fab71b1ef7e1573ff14dbde54407
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_accent));
        }
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