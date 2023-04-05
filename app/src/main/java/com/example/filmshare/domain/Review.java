package com.example.filmshare.domain;

import com.google.gson.annotations.SerializedName;

public class Review {
    private String id;
    private String author;
    private String content;
    private String url;
    private int rating;
    @SerializedName("author_details")
    private AuthorDetails authorDetails;

    public Review(String id, String author, String content, String url, int rating, AuthorDetails authorDetails) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
        this.rating = rating;
        this.authorDetails = authorDetails;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public String getUrl() {
        return url;
    }

    public AuthorDetails getAuthorDetails() {
        return authorDetails;
    }
}

