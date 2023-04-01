package com.example.filmshare.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie_table")
public class Movie {

    @PrimaryKey(autoGenerate = false)
    private int id;

    @SerializedName("backdrop_path")
    private String backdropPath;
    private int budget;
    @SerializedName("original_language")
    private String language;
    @SerializedName("original_title")
    private String title;
    private String overview;
    private double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    private int revenue;
    private int runtime;
    private String status;
    private String tagline;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public Movie(int id, String backdropPath, int budget, String language, String title, String overview, double popularity, String posterPath, String releaseDate, int revenue, int runtime, String status, String tagline, double voteAverage, int voteCount) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.budget = budget;
        this.language = language;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }


    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", backdropPath='" + backdropPath + '\'' +
                ", budget=" + budget +
                ", language='" + language + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                '}';
    }
}
