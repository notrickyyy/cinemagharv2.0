package com.rikesh.cinemaghar.Core;

import android.content.Context;

import com.rikesh.cinemaghar.Model.Movie;

import java.util.ArrayList;

public interface MovieContract {
    interface View{
        void onMovieFailure(String message);
        void onProcessStart();
        void onProcessEnd();
        void onMovieRead(ArrayList<Movie> movies);
    }

    interface Presenter{
        void readMovies(Context context, String base_url, int pages);
        void searchMovies(Context context, String base_url, int pages, String query);
    }

    interface Interactor{
        void performReadMovies(Context context, String base_url, int pages);
        void performSearchMovies(Context context, String base_url, int pages, String query);
    }

    interface onOperationListener{
        void onFailure(String message);
        void onStart();
        void onEnd();
        void onRead(ArrayList<Movie> movies);
    }
}
