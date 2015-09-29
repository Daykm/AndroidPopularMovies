package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

public class MovieImage {

    @SerializedName("file_path") String filePath;
    Integer width;
    Integer height;
    @SerializedName("iso_639_1") String iso3691;

    @SerializedName("aspect_ratio") Float aspectRatio;
    @SerializedName("vote_average") Float voteAverage;
    @SerializedName("vote_count") Integer voteCount;

    public String getFilePath() {
        return filePath;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getIso3691() {
        return iso3691;
    }

    public Float getAspectRatio() {
        return aspectRatio;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }
}
