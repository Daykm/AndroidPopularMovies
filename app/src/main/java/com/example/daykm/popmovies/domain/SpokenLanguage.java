package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class SpokenLanguage {
    @SerializedName("iso_639_1")
    Locale locale;

    String name;
}
