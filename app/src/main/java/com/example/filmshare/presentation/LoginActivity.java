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


        Button loginButton = findViewById(R.id.login_button);
        authViewModel.createRequestToken(this);

//        Intent intent = getIntent();
//        Uri data = intent.getData();
//        if (data != null) {
//            Log.d("redirect", "Redirected from: " + data.toString());
//        } else {
//            authViewModel.createRequestToken(this);
//            Log.d("token", "First token created" + authViewModel.getRequestToken());
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