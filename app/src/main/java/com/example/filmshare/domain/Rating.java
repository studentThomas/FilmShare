package com.example.filmshare.domain;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("value")
    private double value;

    public Rating(double value) {
        this.value = value;
    }
}
