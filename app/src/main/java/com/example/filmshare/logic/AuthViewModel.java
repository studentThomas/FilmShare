package com.example.filmshare.logic;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.SessionRequest;
import com.example.filmshare.domain.response.SessionResponse;
import com.example.filmshare.domain.response.TokenResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthViewModel extends ViewModel {
    private MovieShareApi movieShareApi;
    private String apiKey;
    private String requestToken;

    public AuthViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieShareApi = retrofit.create(MovieShareApi.class);
        apiKey = "b524ecf04a4dde849cafa595bf86982b";
    }

    public void getRequestToken(Context context) {
        Call<TokenResponse> call = movieShareApi.getRequestToken(apiKey);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    requestToken = tokenResponse.getRequestToken();

//                    apiKey = requestToken;

                    String url = "https://www.themoviedb.org/authenticate/" + requestToken;
                    Uri webpage = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    context.startActivity(intent);
                    Log.d("token", "token:" + requestToken);
                    createSession(requestToken);
                } else {
                    Log.d("token", "token:" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("token", "token:" + t.getMessage());
            }
        });



    }


    public void createSession(String requestToken) {

        Call<SessionResponse> call = movieShareApi.createSession(apiKey, new SessionRequest(requestToken));


        Log.d("token", "session stared:");
        call.enqueue(new Callback<SessionResponse>() {
            @Override
            public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                if (response.isSuccessful()) {
                    SessionResponse sessionResponse = response.body();
                    String sessionId = sessionResponse.getSessionId();
                    Log.d("token", "sessionId:" + sessionId);
                } else {
                    Log.d("token", "error:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<SessionResponse> call, Throwable t) {
                Log.d("token", "error:" + t.getMessage());
            }
        });
    }

    public String getRequestToken() {
        return requestToken;
    }




}
