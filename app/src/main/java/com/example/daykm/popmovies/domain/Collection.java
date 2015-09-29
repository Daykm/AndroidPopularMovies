package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

public class Collection {
    Integer id;
    String name;
    @SerializedName("poster_path") String posterPath;
    @SerializedName("backdrop_path") String backdropPath;
}
