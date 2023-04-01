package com.example.filmshare.domain.response;

import com.example.filmshare.domain.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> movies;

    @SerializedName("items")
    private List<Movie> listItems;

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Movie> getListItems() {
        return listItems;
    }
}
