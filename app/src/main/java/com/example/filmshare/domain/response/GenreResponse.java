package com.example.filmshare.domain.response;

import com.example.filmshare.domain.Genre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {

    @SerializedName("genres")
    private List<Genre> genres;


    public List<Genre> getGenres() {
        return genres;
    }
}
