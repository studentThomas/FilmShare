package com.example.filmshare.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.filmshare.R;
import com.example.filmshare.domain.response.TokenResponse;
import com.example.filmshare.logic.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AuthViewModel authViewModel = new AuthViewModel();
        TokenResponse tokenResponse = new TokenResponse();

        Button loginButton = findViewById(R.id.login_button);

        authViewModel.getRequestToken(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authViewModel.createSession(authViewModel.getRequestToken());

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });




    }
}