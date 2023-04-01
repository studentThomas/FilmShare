package com.example.filmshare.domain.response;

import com.google.gson.annotations.SerializedName;

public class ListItemRequest {
    @SerializedName("media_id")
    private int mediaId;

    public ListItemRequest(int mediaId) {
        this.mediaId = mediaId;
    }
}
