package com.example.filmshare.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.filmshare.R;
import com.example.filmshare.domain.response.TokenResponse;
import com.example.filmshare.logic.AuthViewModel;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_accent));
        }
        setContentView(R.layout.activity_login);

        AuthViewModel authViewModel = new AuthViewModel();


        Button loginButton = findViewById(R.id.login_button);
        authViewModel.createRequestToken(this);

//        Intent intent = getIntent();
//        Uri data = intent.getData();
//        if (data != null) {
//            loginButton.setVisibility(View.VISIBLE);
//        } else {
//            loginButton.setVisibility(View.INVISIBLE);
//        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authViewModel.createSession(authViewModel.getRequestToken(), LoginActivity.this);

//                Toast.makeText(LoginActivity.this, "sessionid:" + authViewModel.getSessionId(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);

            }
        });




    }
}