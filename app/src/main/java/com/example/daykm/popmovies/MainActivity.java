package com.example.daykm.popmovies;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String RESULT_KEY = "A";

    public static final String SERVICE = "B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isSinglePane = false;

        if(findViewById(R.id.detailsContainer) == null) {
            isSinglePane = true;
        }

        if(savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(getSupportFragmentManager().findFragmentByTag(SERVICE) == null) {
                ServiceFragment fragment = new ServiceFragment();
                fragment.setRetainInstance(true);
                ft.add(fragment, SERVICE);
            }
            ft.add(R.id.posterContainer, PosterListFragment.newInstance(isSinglePane));
            ft.commit();
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
