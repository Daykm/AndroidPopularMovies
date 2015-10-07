package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class MovieVideo {
    String id;
    @SerializedName("iso_639_1") Locale locale;
    String key;
    String name;
    String site;
    Integer size;
    String type;

    public String getId() {
        return id;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public Integer getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
