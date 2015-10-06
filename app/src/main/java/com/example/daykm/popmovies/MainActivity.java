package com.example.daykm.popmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String SERVICE = "B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(getSupportFragmentManager().findFragmentByTag(SERVICE) == null) {
                ServiceFragment fragment = new ServiceFragment();
                fragment.setRetainInstance(true);
                ft.add(fragment, SERVICE);
            }
            ft.add(R.id.posterContainer, PosterListFragment.newInstance(), PosterListFragment.TAG);
            ft.commit();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        // Handle rotation changes

        boolean isSinglePane = findViewById(R.id.detailsContainer) == null;

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentByTag(MovieDetailsFragment.TAG);
        if(fragment != null) {
            if(isSinglePane) {
                fm.beginTransaction().remove(fragment).commit();
                fm.executePendingTransactions();
                fm.beginTransaction()
                        .add(R.id.posterContainer, fragment, MovieDetailsFragment.TAG)
                        .hide(fm.findFragmentByTag(PosterListFragment.TAG))
                        .addToBackStack(PosterListFragment.FRAGMENT_TRANSACTION_TAG)
                        .commit();
            } else {
                fm.popBackStack(PosterListFragment.FRAGMENT_TRANSACTION_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.executePendingTransactions();
                fm.beginTransaction()
                        .add(R.id.detailsContainer, fragment, MovieDetailsFragment.TAG)
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
