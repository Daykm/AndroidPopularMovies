package com.example.daykm.popmovies.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class ProductionCountry {
    @SerializedName("iso_3166_1") Locale locale;
    String name;
}
