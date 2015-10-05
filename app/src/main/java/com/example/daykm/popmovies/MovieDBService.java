package com.example.daykm.popmovies;

import android.util.Log;

import com.example.daykm.popmovies.domain.Configuration;
import com.example.daykm.popmovies.domain.MovieImagesList;
import com.example.daykm.popmovies.domain.Movie;
import com.example.daykm.popmovies.domain.MovieDiscoveryPage;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class MovieDBService {

    private interface HttpService {
        @GET("discover/movie")
        Call<MovieDiscoveryPage> getPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("sort_by") String sortingCriteria);

        @GET("movie/{id}")
        Call<Movie> getMovieById( @Path("id") Integer id, @Query("api_key") String apiKey);

        @GET("configuration")
        Call<Configuration> getConfiguration(@Query("api_key") String apiKey);


    }

    private HttpService mService;

    private String apiKey;

    public MovieDBService(String apiKey) {
        this.apiKey = apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(HttpService.class);
    }


    public void getPopularMovies(Callback<MovieDiscoveryPage> callback, String sortingCriteria) {
        mService.getPopularMovies(apiKey, 1, sortingCriteria).enqueue(callback);
    }

    public void getConfiguration(Callback<Configuration> callback) {
        mService.getConfiguration(apiKey).enqueue(callback);
    }

    public void getMovieDetails(Integer id, Callback<Movie> callback) {
        mService.getMovieById(id, apiKey).enqueue(callback);
    }

}
