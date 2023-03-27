package com.example.filmshare.domain;

import com.google.gson.annotations.SerializedName;

public class RequestToken {
    @SerializedName("success")
    private boolean success;
    @SerializedName("expires_at")
    private String expiresAt;
    @SerializedName("request_token")
    private String requestToken;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
