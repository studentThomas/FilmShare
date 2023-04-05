package com.example.filmshare.domain;

import com.google.gson.annotations.SerializedName;

public class Review {
    private String id;
    private String author;
    private String content;
    private String url;
    @SerializedName("author_details")
    private AuthorDetails authorDetails;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public AuthorDetails getAuthorDetails() {
        return authorDetails;
    }
}

