package com.example.daykm.popmovies;

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
import com.example.daykm.popmovies.domain.SortByCriteria;

import retrofit.Callback;
import retrofit.Response;


public class PosterListFragment extends Fragment {

    public static final String TAG = PosterListFragment.class.getSimpleName();

    public static final String FRAGMENT_TRANSACTION_TAG = "t";

    RecyclerViewAdapter adapter;

    MovieDBService service;

    SortedList<MovieListItem> movies;

    private String baseUrl = "";

    private String posterSize = "";

    private static final String PANE = "P";
    private static final String SORT = "S";
    boolean isSinglePane = false;

    private String sortingCriteria = SortByCriteria.POPULARITY_DESC;

    public PosterListFragment() {
        // Required empty public constructor
    }

    public static PosterListFragment newInstance() {
        PosterListFragment fragment = new PosterListFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT, sortingCriteria);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        adapter = new RecyclerViewAdapter(getContext(), new PosterListenerCallback());
        movies = adapter.getList();
        if(savedInstanceState != null) {
            sortingCriteria = savedInstanceState.getString(SORT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        isSinglePane = getActivity().findViewById(R.id.detailsContainer) == null;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poster_list, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.posterList);
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
                                sortingCriteria = SortByCriteria.POPULARITY_DESC;
                                break;
                            case 1:
                                sortingCriteria = SortByCriteria.POPULARITY_ASC;
                                break;
                            case 2:
                                sortingCriteria = SortByCriteria.VOTE_AVERAGE_DESC;
                                break;
                            case 3:
                                sortingCriteria = SortByCriteria.VOTE_AVERAGE_ASC;
                                break;
                        }
                        service.getPopularMovies(new PopularMoviesCallback(), sortingCriteria);
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
            service.getPopularMovies(new PopularMoviesCallback(), sortingCriteria);
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }

    private class PopularMoviesCallback implements Callback<MovieDiscoveryPage> {

        @Override
        public void onResponse(Response<MovieDiscoveryPage> response) {
            movies.beginBatchedUpdates();
            movies.clear();
            for (MovieDiscovery movie : response.body().getResults()) {
                movies.add(new MovieListItem(movie.getId(), baseUrl + posterSize +  movie.getPosterPath(), movie.getPopularity(), movie.getVoteAverage()));
            }
            movies.endBatchedUpdates();
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
            if(isSinglePane) {
                Fragment frag = getFragmentManager().findFragmentByTag(TAG);
                getFragmentManager().beginTransaction()
                        .add(R.id.posterContainer, MovieDetailsFragment.newInstance(movie.getId(), movie.getPosterUrl()), MovieDetailsFragment.TAG)
                        .addToBackStack(FRAGMENT_TRANSACTION_TAG)
                        .hide(frag) // keep instance
                        .commit();
            } else {
                // TODO tablet layout
                getFragmentManager().beginTransaction()
                        .replace(R.id.detailsContainer, MovieDetailsFragment.newInstance(movie.getId(), movie.getPosterUrl()), MovieDetailsFragment.TAG)
                        .commit();
            }
        }
    }
}
