package com.example.daykm.popmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daykm.popmovies.domain.Movie;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.HTTP;

public class MovieDetailsFragment extends Fragment {

    public static final String TAG = MovieDetailsFragment.class.getSimpleName();

    private static final String IDENTIFICATION = "ID";
    private static final String POSTER_URL = "P";

    private int id;
    private String posterUrl;

    MovieDBService service;

    @Bind(R.id.movieTitle) TextView title;
    @Bind(R.id.moviePoster) ImageView poster;
    @Bind(R.id.movieReleaseYear) TextView year;
    @Bind(R.id.movieRuntime) TextView runtime;
    @Bind(R.id.movieRating) TextView rating;
    @Bind(R.id.movieFavoriteButton) Button favorite;
    @Bind(R.id.movieOverview) TextView overview;
    @Bind(R.id.popularity) TextView popularity;

    public static MovieDetailsFragment newInstance(Integer id, String posterURL) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(IDENTIFICATION, id);
        args.putString(POSTER_URL, posterURL);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(IDENTIFICATION);
        posterUrl = getArguments().getString(POSTER_URL);

        service = ((ServiceFragment) getFragmentManager().findFragmentByTag(MainActivity.SERVICE)).service;
        if(service == null && getFragmentManager().executePendingTransactions()) {
            service = ((ServiceFragment) getFragmentManager().findFragmentByTag(MainActivity.SERVICE)).service;
        }
        service.getMovieDetails(id, new MovieDetailsCallback());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    private class MovieDetailsCallback implements Callback<Movie> {
        @Override
        public void onResponse(Response<Movie> response) {
            if(response.code() == 200) {
                inflateFromMovie(response.body());
            } else {
                Log.e(TAG, "Network error");
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("Service", t.getCause().toString());
        }
    }

    public void inflateFromMovie(Movie result) {
        title.setText(result.getTitle());

        Picasso.with(getContext()).load(posterUrl).into(poster, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                // success
            }

            @Override
            public void onError() {
                poster.setImageResource(R.drawable.no_poster);
            }
        });
        year.setText(result.getReleaseDate().substring(0, 4));
        runtime.setText(result.getRuntime().toString());
        rating.setText(new DecimalFormat("#.#").format(result.getVoteAverage()) + "/" + "10.0");
        overview.setText(result.getOverview());
        popularity.setText(Float.toString(result.getPopularity()));
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Details", "TODO");
            }
        });
    }

}
