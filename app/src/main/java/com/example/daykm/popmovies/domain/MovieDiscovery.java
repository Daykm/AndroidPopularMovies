package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class MovieDiscovery {

    Boolean adult;

    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("genre_ids")
    List<Integer> genreIds;

    Integer id;

    @SerializedName("original_language")
    Locale originalLanguage;

    @SerializedName("original_title")
    String originalTitle;

    String overview;

    @SerializedName("release_date")
    String releaseDate;

    @SerializedName("poster_path")
    String posterPath;

    Float popularity;

    String title;

    Boolean video;

    @SerializedName("vote_average")
    Float voteAverage;

    @SerializedName("vote_count")
    Integer voteCount;

    public Boolean getAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public Integer getId() {
        return id;
    }

    public Locale getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getVideo() {
        return video;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }
}
