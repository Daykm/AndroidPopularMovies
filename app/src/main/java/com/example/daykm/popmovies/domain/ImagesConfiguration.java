package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesConfiguration {
    @SerializedName("base_url") String baseUrl;
    @SerializedName("secure_base_url") String secureBaseUrl;
    @SerializedName("backdrop_sizes") List<String> backdropSizes;
    @SerializedName("logo_sizes") List<String> logoSizes;
    @SerializedName("poster_sizes") List<String> posterSizes;
    @SerializedName("profile_sizes") List<String> profileSizes;
    @SerializedName("still_sizes") List<String> stillSizes;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public List<String> getStillSizes() {
        return stillSizes;
    }
}
