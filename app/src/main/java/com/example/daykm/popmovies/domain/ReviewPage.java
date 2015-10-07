package com.example.daykm.popmovies.domain;

import java.util.List;

public class ReviewPage {

    Integer id;
    Integer page;

    List<Review> results;

    public Integer getId() {
        return id;
    }

    public Integer getPage() {
        return page;
    }

    public List<Review> getResults() {
        return results;
    }
}
