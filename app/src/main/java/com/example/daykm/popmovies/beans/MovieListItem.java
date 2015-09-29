package com.example.daykm.popmovies.beans;

public class MovieListItem {

    Integer id;

    String posterUrl;

    Float popularity;
    Float rating;

    public MovieListItem(Integer id, String posterUrl, Float popularity, Float rating) {
        this.id = id;
        this.posterUrl = posterUrl;
        this.popularity = popularity;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public Float getPopularity() {
        return popularity;
    }

    public Float getRating() {
        return rating;
    }
}
