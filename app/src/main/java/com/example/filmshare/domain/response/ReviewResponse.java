package com.example.filmshare.domain.response;

import com.example.filmshare.domain.Review;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    @SerializedName("results")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }
}
