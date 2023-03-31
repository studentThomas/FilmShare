package com.example.filmshare.domain.response;

import com.example.filmshare.domain.List;
import com.google.gson.annotations.SerializedName;

public class ListResponse {

    @SerializedName("results")
    private java.util.List<List> results;

    public java.util.List<List> getLists() {
        return results;
    }
}
