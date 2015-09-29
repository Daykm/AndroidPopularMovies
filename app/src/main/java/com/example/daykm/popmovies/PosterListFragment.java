package com.example.daykm.popmovies;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.daykm.popmovies.beans.MovieListItem;
import com.example.daykm.popmovies.domain.Configuration;
import com.example.daykm.popmovies.domain.MovieDiscovery;
import com.example.daykm.popmovies.domain.MovieDiscoveryPage;

import retrofit.Callback;
import retrofit.Response;


public class PosterListFragment extends Fragment {

    RecyclerViewAdapter adapter;

    MovieDBService service;

    SortedList<MovieListItem> movies;

    private String baseUrl = "";

    private String posterSize = "";

    private static final String PANE = "P";
    boolean isSinglePane = false;

    public PosterListFragment() {
        // Required empty public constructor
    }

    public static PosterListFragment newInstance(boolean isSinglePane) {
        PosterListFragment fragment = new PosterListFragment();
        Bundle args = new Bundle();
        args.putByte(PANE, (byte) (isSinglePane ? 0x0 : 0x1));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isSinglePane = getArguments().getByte(PANE) == 0x0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poster_list, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.posterList);


        adapter = new RecyclerViewAdapter(getContext(), new PosterListenerCallback());
        movies = adapter.getList();
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_poster_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort:
                Log.i("Sort", "Clicked");
                SortDialog.newInstance(new SortDialog.SortChangeCallback() {
                    @Override
                    public void onSortChange(int which) {
                        switch(which) {
                            case 0:
                                // popularity
                                adapter.setSortingAdapter(new RecyclerViewAdapter.PosterListByPopularity(adapter));
                                break;
                            case 1:
                                // rating
                                adapter.setSortingAdapter(new RecyclerViewAdapter.PosterListByRating(adapter));
                                break;

                        }
                    }
                }).show(getFragmentManager(), "Dialog");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        service = ((ServiceFragment) getFragmentManager().findFragmentByTag(MainActivity.SERVICE)).service;
        if(service == null && getFragmentManager().executePendingTransactions()) {
            service = ((ServiceFragment) getFragmentManager().findFragmentByTag(MainActivity.SERVICE)).service;
        }
        service.getConfiguration(new ConfigurationCallback());
    }

    private class ConfigurationCallback implements Callback<Configuration> {

        @Override
        public void onResponse(Response<Configuration> response) {
            baseUrl = response.body().getImages().getBaseUrl();
            posterSize = response.body().getImages().getPosterSizes().get(3);
            service.getPopularMovies(new PopularMoviesCallback());
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }

    private class PopularMoviesCallback implements Callback<MovieDiscoveryPage> {

        @Override
        public void onResponse(Response<MovieDiscoveryPage> response) {
            for (MovieDiscovery movie : response.body().getResults()) {
                movies.add(new MovieListItem(movie.getId(), baseUrl + posterSize +  movie.getPosterPath(), movie.getPopularity(), movie.getVoteAverage()));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            // do not fail
        }
    }

    public interface PosterListener {
        void onPosterClicked(MovieListItem movie);
    }

    public class PosterListenerCallback implements  PosterListener {

        @Override
        public void onPosterClicked(MovieListItem movie) {
            // do something
            Log.i("IT WORKED", movie.getId().toString());
            if(isSinglePane) {
                getFragmentManager().beginTransaction().replace(R.id.posterContainer, MovieDetailsFragment.newInstance(movie.getId(), movie.getPosterUrl())).addToBackStack(null).commit();
            } else {
                // TODO tablet layout
            }
        }
    }

}
