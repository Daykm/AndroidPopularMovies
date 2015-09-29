package com.example.daykm.popmovies;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.daykm.popmovies.beans.MovieListItem;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PosterViewHolder> {

    SortedList<MovieListItem> movies;
    Context context;
    PosterListFragment.PosterListener listener;
    SortedListAdapterCallback<MovieListItem> adapterCallback;

    public static final class PosterListByPopularity extends SortedListAdapterCallback<MovieListItem> {

        public PosterListByPopularity(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(MovieListItem movie1, MovieListItem movie2) {
            return -1 * Float.compare(movie1.getPopularity(), movie2.getPopularity());
        }

        @Override
        public boolean areContentsTheSame(MovieListItem oldItem, MovieListItem newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(MovieListItem item1, MovieListItem item2) {
            return false;
        }
    }

    public static final class PosterListByRating extends SortedListAdapterCallback<MovieListItem> {


        public PosterListByRating(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(MovieListItem movie1, MovieListItem movie2) {
            return -1 * Float.compare(movie1.getRating(), movie2.getRating());
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
        adapterCallback = new PosterListByRating(this);
        movies = new SortedList<>(MovieListItem.class, adapterCallback);
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

    public void setSortingAdapter(SortedListAdapterCallback<MovieListItem> adapterCallback) {
        this.adapterCallback = adapterCallback;
        notifyDataSetChanged();
    }
}
