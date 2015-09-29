package com.example.daykm.popmovies;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.daykm.popmovies.beans.MovieListItem;
import com.squareup.picasso.Picasso;

import java.util.Comparator;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PosterViewHolder> {

    SortedList<MovieListItem> movies;
    Context context;
    PosterListFragment.PosterListener listener;

    public static final int SORT_BY_POPULARITY = 1;
    public static final int SORT_BY_RATING = 2;
    private int currentSort = 1;

    public final Comparator<MovieListItem> MOVIE_COMPARATOR = new Comparator<MovieListItem>() {
        @Override
        public int compare(MovieListItem movie1, MovieListItem movie2) {
            switch(currentSort) {
                case SORT_BY_POPULARITY:
                    Log.i("RecyclerViewAdapter", "Sort by popularity");
                    return -1 * Float.compare(movie1.getPopularity(), movie2.getPopularity());
                case SORT_BY_RATING:
                    Log.i("RecyclerViewAdapter", "Sort by rating");
                    return -1 * Float.compare(movie1.getRating(), movie2.getRating());
            }
            return 0;
        }
    };

    public final class PosterListCallback extends SortedListAdapterCallback<MovieListItem> {

        public PosterListCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(MovieListItem movie1, MovieListItem movie2) {
            return MOVIE_COMPARATOR.compare(movie1, movie2);
        }

        @Override
        public boolean areContentsTheSame(MovieListItem movie1, MovieListItem movie2) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(MovieListItem movie1, MovieListItem movie2) {
            return false;
        }
    }

    public RecyclerViewAdapter(Context context, PosterListFragment.PosterListener listener) {
        this.context = context;
        this.listener = listener;
        movies = new SortedList<>(MovieListItem.class, new PosterListCallback(this));
    }

    protected class PosterViewHolder extends RecyclerView.ViewHolder {
        int index;
        ImageView poster;
        public PosterViewHolder(View itemView, final PosterListFragment.PosterListener listener) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);

            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPosterClicked(movies.get(index));
                }
            });

        }
    }

    public SortedList<MovieListItem> getList() {
        return movies;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.poster_view, viewGroup, false);
        PosterViewHolder vh = new PosterViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder viewHolder, int i) {
        String url = movies.get(i).getPosterUrl();
        Picasso.with(context).load(url).into(viewHolder.poster);
        viewHolder.index = i;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @UiThread
    public void setSortingAdapter(int which) {
        currentSort = which;
        movies.beginBatchedUpdates();
        for(int i = 0; i < movies.size(); i++) {
            movies.recalculatePositionOfItemAt(i);
        }
        movies.endBatchedUpdates();
        Log.i("CHANGE", Integer.toString(which));
    }
}
