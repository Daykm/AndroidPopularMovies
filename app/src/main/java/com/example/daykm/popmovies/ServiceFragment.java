package com.example.daykm.popmovies;

import android.content.Context;
import android.support.v4.app.Fragment;

public class ServiceFragment extends Fragment {

    MovieDBService service;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        service = new MovieDBService(getResources().getString(R.string.movieDBKey));
    }


}
