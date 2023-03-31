package com.example.filmshare.logic;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.User;
import com.example.filmshare.domain.response.SessionRequest;
import com.example.filmshare.domain.response.SessionResponse;
import com.example.filmshare.domain.response.TokenResponse;
import com.example.filmshare.presentation.LoginActivity;
import com.example.filmshare.presentation.MainActivity;

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

    public void createRequestToken(Context context) {
        Call<TokenResponse> call = movieShareApi.getRequestToken(apiKey);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    requestToken = tokenResponse.getRequestToken();

                    String url = "https://www.themoviedb.org/authenticate/" + requestToken;

//                    String url = "https://www.themoviedb.org/authenticate/" + requestToken + "?redirect_to=filmshare://auth/approved";
                    Uri webpage = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    context.startActivity(intent);
                    Log.d("token", "token: " + requestToken);
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


    public void createSession(String requestToken, Context context) {

        Call<SessionResponse> call = movieShareApi.createSession(apiKey, new SessionRequest(requestToken));

//        String url = "https://www.themoviedb.org/authenticate/" + requestToken + "?redirect_to=filmshare://auth/approved";
//        Uri webpage = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//        context.startActivity(intent);


        Log.d("token", "session stared:");
//        Log.d("token", requestToken);
        call.enqueue(new Callback<SessionResponse>() {
            @Override
            public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                if (response.isSuccessful()) {
                    SessionResponse sessionResponse = response.body();
                    String sessionId = sessionResponse.getSessionId();


                    SessionManager.getInstance().setSessionId(sessionId);
                    getUserId();

                    Log.d("messageid", "sessionId:" + sessionId);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);


                } else {
                    Log.d("messageid", "error:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<SessionResponse> call, Throwable t) {
                Log.d("messageid", "error:" + t.getMessage());
            }
        });
    }

    public void getUserId() {
        String sessionId = SessionManager.getInstance().getSessionId();
        Call<User> call = movieShareApi.getAccountDetails(sessionId, apiKey);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    int userId = user.getId();
                    SessionManager.getInstance().setUserId(userId);
                    Log.d("userid", "userId:" + userId);
                } else {
                    Log.d("userid", "error:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("userid", "error:" + t.getMessage());
            }
        });
    }

    public String getRequestToken() {
        return requestToken;
    }





}
