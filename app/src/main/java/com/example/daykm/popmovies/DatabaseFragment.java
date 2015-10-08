package com.example.daykm.popmovies;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.daykm.popmovies.greendao.DaoMaster;
import com.example.daykm.popmovies.greendao.DaoSession;
import com.example.daykm.popmovies.greendao.FavoriteMovieDao;

public class DatabaseFragment extends Fragment {

    FavoriteMovieDao dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "lease-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        dao = daoSession.getFavoriteMovieDao();
    }
}
