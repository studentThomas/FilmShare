package com.example.filmshare.logic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.filmshare.domain.Review;
import com.example.filmshare.domain.response.ReviewResponse;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {

    private static ReviewResponse reviewResponse;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewResponse = new ReviewResponse();
    }

    public List<Review> getReviews() {
        return reviewResponse.getReviews();
    }
}
