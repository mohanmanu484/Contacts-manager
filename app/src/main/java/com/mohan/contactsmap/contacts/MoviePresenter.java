/*
package com.mohan.contactsmap.contacts;

import android.support.annotation.NonNull;

import com.mohan.internal.mvpexample.model.MovieDataSource;
import com.mohan.internal.mvpexample.model.MovieRepository;
import com.mohan.internal.mvpexample.pojo.Movie;

import java.util.List;


*/
/**
 * Created by mohan on 4/10/16.
 *//*


public class MoviePresenter implements ContactsContract.ContactsListPresenter {

    private final ContactsRepository mMovieRepository;
    private final ContactsContract.ContactsListView mMovieContactsListView;

    public MoviePresenter(@NonNull ContactsRepository movieRepository, @NonNull ContactsContract.ContactsListView movieContactsListView) {

        if (null == movieRepository) {
            throw new IllegalArgumentException("movieRepository cannot be null");
        }
        if (null == movieContactsListView) {
            throw new IllegalArgumentException("movieContactsListView cannot be null!");
        }
        mMovieRepository = movieRepository;
        mMovieContactsListView = movieContactsListView;

        mMovieContactsListView.setPresenter(this);
    }

    @Override
    public void refreshMovies() {
        loadMovies();
    }

    @Override
    public void start() {
        mMovieContactsListView.setTitle("Movies List");
        loadMovies();
    }



    private void loadMovies() {
        mMovieContactsListView.showProgress();
        mMovieRepository.getMovies(new MovieDataSource.LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {

                if(!mMovieContactsListView.isActive()){
                    return;
                }

                mMovieContactsListView.hideProgress();
                mMovieContactsListView.showMovies(movies);
            }

            @Override
            public void onDataNotAvailable() {

                if(!mMovieContactsListView.isActive()){
                    return;
                }
                mMovieContactsListView.hideProgress();
            }

            @Override
            public void onNetworkError(String message) {

                if(!mMovieContactsListView.isActive()){
                    return;
                }
                mMovieContactsListView.hideProgress();
                mMovieContactsListView.showNetworkError(message);
            }
        });
    }
}
*/
