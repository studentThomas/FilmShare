package com.example.filmshare.domain.response;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("expires_at")
    private String expiresAt;
    @SerializedName("request_token")
    private String requestToken;

    public boolean isSuccess() {
        return success;
    }
    public String getExpiresAt() {
        return expiresAt;
    }
    public String getRequestToken() {
        return requestToken;
    }


}
