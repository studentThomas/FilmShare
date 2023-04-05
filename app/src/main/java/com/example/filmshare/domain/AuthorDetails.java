package com.example.filmshare.domain;

import com.google.gson.annotations.SerializedName;

public class AuthorDetails {
    private String name;
    private String username;
    @SerializedName("avatar_path")
    private String avatarPath;
    private double rating;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public double getRating() {
        return rating;
    }
}
