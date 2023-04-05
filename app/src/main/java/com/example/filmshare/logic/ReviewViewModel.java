package com.example.filmshare.logic;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.domain.Review;
import com.example.filmshare.domain.response.ListItemRequest;
import com.example.filmshare.domain.response.ReviewResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewViewModel extends AndroidViewModel {

    private static ReviewResponse reviewResponse;
    private static MovieShareApi movieShareApi;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewResponse = new ReviewResponse();
    }

    public List<Review> getReviews() {
        return reviewResponse.getReviews();
    }

    public void insert(Review review) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        String apiKey = "b524ecf04a4dde849cafa595bf86982b";
        String sessionId = SessionManager.getInstance().getSessionId();
        MovieShareApi service = retrofit.create(MovieShareApi.class);
        
        Call<Review> call = service.addReview(review.getId(), apiKey, sessionId, review);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Log.d("ListRepository", "Success inserting listitem: " + response.body().toString());
                } else {
                    Log.d("ListRepository", "Error inserting listitem: " + response.message());
                    Log.d("ListRepository", "Error inserting listitem: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d("ListRepository", "Error inserting list: " + t.getMessage());
            }

        });
    }
}
