package com.example.daykm.popmovies.domain;

import java.util.List;

public class VideoResults {
    Integer id;
    List<MovieVideo> results;

    public Integer getId() {
        return id;
    }

    public List<MovieVideo> getResults() {
        return results;
    }
}
