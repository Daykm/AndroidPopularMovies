package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    Boolean adult;
    @SerializedName("backdrop_path") String backdropPath;
    @SerializedName("belongs_to_collection") Collection collection;
    Integer budget;
    List<Genre> genres;
    String homepage;
    Integer id;
    @SerializedName("imdb_id") String imdbId;
    @SerializedName("original_language") String originalLanguage;
    @SerializedName("original_title") String originalTitle;
    String overview;
    Float popularity;
    @SerializedName("poster_path") String posterPath;
    @SerializedName("production_companies") List<ProductionCompany> productionCompanies;
    @SerializedName("production_countries") List<ProductionCountry> productionCountries;
    @SerializedName("release_date") String releaseDate;
    Integer revenue;
    Integer runtime;
    @SerializedName("spoken_languages") List<SpokenLanguage> spokenLanguages;
    String status;
    String tagline;
    String title;
    Boolean video;
    @SerializedName("vote_average") Float voteAverage;
    @SerializedName("vote_count") Integer voteCount;

    public Boolean getAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Collection getCollectionId() {
        return collection;
    }

    public Integer getBudget() {
        return budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public Integer getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
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
