package com.example.filmshare.domain;

import com.google.gson.annotations.SerializedName;

public class SessionRequest {
    @SerializedName("request_token")
    private String requestToken;

    public SessionRequest(String requestToken) {
        this.requestToken = requestToken;
    }
}
