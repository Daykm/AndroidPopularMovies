package com.example.daykm.popmovies;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daykm.popmovies.domain.Movie;
import com.example.daykm.popmovies.domain.MovieVideo;
import com.example.daykm.popmovies.domain.Review;
import com.example.daykm.popmovies.domain.ReviewPage;
import com.example.daykm.popmovies.domain.VideoResults;
import com.example.daykm.popmovies.domain.VideoTypes;
import com.example.daykm.popmovies.greendao.DaoSession;
import com.example.daykm.popmovies.greendao.FavoriteMovie;
import com.example.daykm.popmovies.greendao.FavoriteMovieDao;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;

public class MovieDetailsFragment extends Fragment {

    public static final String TAG = MovieDetailsFragment.class.getSimpleName();

    private static final String IDENTIFICATION = "ID";
    private static final String POSTER_URL = "P";

    private int id;
    private String posterUrl;

    VideoResults trailers;
    ReviewPage reviews;
    Movie movie;

    View fragmentView;
    @Bind(R.id.movieTitle) TextView title;
    @Bind(R.id.moviePoster) ImageView poster;
    @Bind(R.id.movieReleaseYear) TextView year;
    @Bind(R.id.movieRuntime) TextView runtime;
    @Bind(R.id.movieRating) TextView rating;
    @Bind(R.id.movieFavoriteButton) Button favorite;
    @Bind(R.id.movieOverview) TextView overview;
    @Bind(R.id.popularity) TextView popularity;
    @Bind(R.id.reviewListView) LinearLayout reviewList;
    @Bind(R.id.trailerListView) LinearLayout trailerList;

    public static MovieDetailsFragment newInstance(Integer id, String posterURL) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(IDENTIFICATION, id);
        args.putString(POSTER_URL, posterURL);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, fragmentView);

        // hide the empty views
        fragmentView.setVisibility(View.INVISIBLE);

        id = getArguments().getInt(IDENTIFICATION);
        posterUrl = getArguments().getString(POSTER_URL);

        MovieDBService service = ((ServiceFragment) getFragmentManager().findFragmentByTag(MainActivity.SERVICE)).service;

        // don't call the api again if we have the data
        if(movie == null) {
            service.getMovieDetails(id, new MovieDetailsCallback());
        } else {
            inflateFromMovie(movie);
            fragmentView.setVisibility(View.VISIBLE);
        }
        if(trailers == null) {
            service.getVideosForMovie(id, new TrailerListCallback());
        } else {
            inflateTrailers(trailers.getResults());
        }
        if(reviews == null) {
            service.getReviewsForMovie(id, new ReviewListCallback());
        } else {
            inflateReviews(reviews.getResults());
        }

        return fragmentView;
    }

    private class MovieDetailsCallback implements Callback<Movie> {
        @Override public void onResponse(Response<Movie> response) {
            if(response.code() == 200) {
                movie = response.body();
                inflateFromMovie(response.body());
                fragmentView.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "Network error");
            }
        }

        @Override public void onFailure(Throwable t) {
            Log.e("Service", t.getCause().toString());
        }
    }

    public class TrailerListCallback implements Callback<VideoResults> {
        @Override public void onResponse(Response<VideoResults> response) {
            trailers = response.body();
            inflateTrailers(trailers.getResults());
        }

        @Override public void onFailure(Throwable t) {
            // don't care
        }
    }

    public class ReviewListCallback implements Callback<ReviewPage> {
        @Override public void onResponse(Response<ReviewPage> response) {
            reviews = response.body();
            inflateReviews(reviews.getResults());
        }

        @Override public void onFailure(Throwable t) {
             // don't care
        }
    }

    public void inflateFromMovie(Movie result) {
        title.setText(result.getTitle());

        Picasso.with(getContext()).load(posterUrl).into(poster, new com.squareup.picasso.Callback() {
            @Override public void onSuccess() {
                // success
            }

            @Override public void onError() {
                poster.setImageResource(R.drawable.no_poster);
            }
        });

        year.setText(result.getReleaseDate().substring(0, 4));
        runtime.setText(result.getRuntime().toString());
        rating.setText(new DecimalFormat("#.#").format(result.getVoteAverage()) + "/" + "10.0");
        overview.setText(result.getOverview());
        popularity.setText(Float.toString(result.getPopularity()));
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                FavoriteMovieDao dao = ((DatabaseFragment) getFragmentManager().findFragmentByTag(MainActivity.DATABASE)).dao;
                FavoriteMovie fav = new FavoriteMovie();
                fav.setMovieid(movie.getId());
                try {
                    dao.insert(fav);
                } catch (SQLiteConstraintException e) {
                    Log.i(TAG, "Movie already favorited");
                }
            }
        });
    }

    public void inflateReviews(List<Review> reviews) {
        for(Review review : reviews) {
            View listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_review, reviewList, true);
            TextView author = (TextView) listItem.findViewById(R.id.author);
            TextView content = (TextView) listItem.findViewById(R.id.reviewContent);
            author.setText(review.getAuthor());
            content.setText(review.getContent());
        }
    }

    public void inflateTrailers(List<MovieVideo> trailers) {
        String baseUrl = getContext().getString(R.string.youtubeUrlBase);
        LayoutInflater inflater =  LayoutInflater.from(getContext());
        for(MovieVideo video : trailers) {
            if(VideoTypes.TRAILER.equals(video.getType())) {
                View listItem = inflater.inflate(R.layout.list_trailer, trailerList, false);

                TextView title = (TextView) listItem.findViewById(R.id.title);
                ImageView play = (ImageView) listItem.findViewById(R.id.playButton);

                title.setText(video.getName());
                play.setOnClickListener(new ClickListener(baseUrl + video.getKey()));

                trailerList.addView(listItem);
            }
        }
    }

    private class  ClickListener implements View.OnClickListener {
        String url;

        public ClickListener(String url) {
            this.url = url;
        }

        @Override public void onClick(View v) {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

}
