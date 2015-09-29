package com.example.daykm.popmovies.domain;

import java.util.List;

public class MovieImagesList {

    Integer id;

    List<MovieImage> backdrops;

    List<MovieImage> posters;

    public Integer getId() {
        return id;
    }

    public List<MovieImage> getBackdrops() {
        return backdrops;
    }

    public List<MovieImage> getPosters() {
        return posters;
    }
}
