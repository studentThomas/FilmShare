package com.example.filmshare.logic;

import android.app.Application;
import android.media.Rating;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmshare.datastorage.MovieRepository;
import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.ListItem;
import com.example.filmshare.datastorage.MovieShareApi;
import com.example.filmshare.domain.AuthorDetails;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.domain.Review;
import com.example.filmshare.domain.response.ListItemRequest;
import com.example.filmshare.domain.response.ReviewResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
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

    public void rateMovie(int movieId, double rating) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieShareApi service = retrofit.create(MovieShareApi.class);
        String key = "b524ecf04a4dde849cafa595bf86982b";

        String sessionId = SessionManager.getInstance().getSessionId();

        com.example.filmshare.domain.Rating ratingBody = new com.example.filmshare.domain.Rating(rating);


        Call<ReviewResponse> call = service.rateMovie(movieId, key, sessionId, ratingBody);


        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("Review", "onResponse: succes " + response.body());
                } else {
                    try {
                        Log.d("Review", "Error response code: " + response.code() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("Review", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Review>> getReviews(int movieId) {
        Log.d("Review", "getReviews: " + movieId);

        MutableLiveData<List<Review>> reviews2 = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieShareApi service = retrofit.create(MovieShareApi.class);
        String key = "b524ecf04a4dde849cafa595bf86982b";

        Call<ReviewResponse> call = service.getMovieReviews(movieId, key);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body().getReviews();
                    Log.d("Review", "onResponse: getting autohrs " + reviews.size());

                    for (Review review : reviews) {
                        Log.d("Review", "onResponse: " + review.getAuthor() );
                        AuthorDetails authorDetails = review.getAuthorDetails();
                        Log.d("Review", "onResponse: " + authorDetails.getRating());
                    }
                    reviews2.setValue(reviews);
                } else {
                    Log.d("Review", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("Review", "onFailure: " + t.getMessage());
            }
        });


        return reviews2;



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
