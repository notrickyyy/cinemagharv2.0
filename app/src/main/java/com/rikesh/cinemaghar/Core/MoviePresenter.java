package com.rikesh.cinemaghar.Core;

import android.content.Context;

import com.rikesh.cinemaghar.Model.Movie;

import java.util.ArrayList;

public class MoviePresenter implements MovieContract.Presenter, MovieContract.onOperationListener{
    private MovieContract.View mView;
    private MovieInteractor mInteractor;

    public MoviePresenter(MovieContract.View mView) {
        this.mView = mView;
        mInteractor = new MovieInteractor(this);
    }

    @Override
    public void readMovies(Context context, String base_url, int pages) {
        mInteractor.performReadMovies(context, base_url, pages);
    }

    @Override
    public void searchMovies(Context context, String base_url, int pages, String query) {
        mInteractor.performSearchMovies(context, base_url, pages, query);
    }

    @Override
    public void onFailure(String message) {
        mView.onMovieFailure(message);
    }

    @Override
    public void onStart() {
        mView.onProcessStart();
    }

    @Override
    public void onEnd() {
        mView.onProcessEnd();
    }

    @Override
    public void onRead(ArrayList<Movie> movies) {
        mView.onMovieRead(movies);
    }
}
