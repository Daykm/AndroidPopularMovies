package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configuration {


    ImagesConfiguration images;

    @SerializedName("change_keys")
    List<String> changeKeys;

    public ImagesConfiguration getImages() {
        return images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }
}
