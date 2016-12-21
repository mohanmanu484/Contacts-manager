package com.mohan.contactsmap.contacts;

import android.os.Bundle;

public class MoviesActivity extends BaseActivity {

    private static final String TAG = "MoviesActivity";

    //MoviePresenter mMoviePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MovieFragment moviesFragment =
                (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (moviesFragment == null) {
            // Create the fragment
            moviesFragment = MovieFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), moviesFragment, R.id.contentFrame);
        }*/

        // Create the presenter
      /*  mMoviePresenter = new MoviePresenter(
                new ContactsRepository(), moviesFragment);*/


    }



}
