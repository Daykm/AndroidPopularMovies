package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDiscoveryPage {

    Integer page;

    List<MovieDiscovery> results;

    @SerializedName("total_pages")
    Integer totalPages;

    @SerializedName("total_results")
    Integer totalResults;

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<MovieDiscovery> getResults() {
        return results;
    }

    public Integer getPage() {
        return page;
    }
}
