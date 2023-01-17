package com.rikesh.cinemaghar.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rikesh.cinemaghar.Adapters.MoviesAdapter;
import com.rikesh.cinemaghar.Core.MovieContract;
import com.rikesh.cinemaghar.Core.MoviePresenter;
import com.rikesh.cinemaghar.Model.Movie;
import com.rikesh.cinemaghar.R;
import com.rikesh.cinemaghar.View.MovieDetails;
import com.rikesh.cinemaghar.View.MoviesSearch;
import com.rikesh.cinemaghar.utils.Constants;
import com.rikesh.cinemaghar.utils.Funct;

import java.util.ArrayList;

public class MoviesCatalogue extends Fragment implements MoviesAdapter.onMovieListener, MovieContract.View {
    ArrayList<Movie> movieList;
    RecyclerView recy;
    MoviesAdapter adapter;
    public MoviePresenter mPresenter;
    ProgressBar progressBar;

    public String fragment_name;

    NestedScrollView nest_scr;
    LinearLayout linearL;
    int page = 1;

    public MoviesCatalogue(String fragment_name) {
        this.fragment_name = fragment_name;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar1, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setOnMenuItemClickListener(item -> {
            startActivity(new Intent(getActivity(), MoviesSearch.class));
            return true;
        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root;

        if (!Funct.isNetworkConnected(requireActivity())) {
            root = inflater.inflate(R.layout.no_internet, container, false);
            LinearLayout try_again = root.findViewById(R.id.try_again);

            try_again.setOnClickListener(v -> Funct.RefreshActivity(requireActivity()));
        }
        else
            root = inflater.inflate(R.layout.movies_catalogue, container, false);


        setHasOptionsMenu(true);

        recy = root.findViewById(R.id.recy);
        recy.setNestedScrollingEnabled(false);
        progressBar = root.findViewById(R.id.progressBar);
        nest_scr = root.findViewById(R.id.scroll_view);
        nest_scr.setNestedScrollingEnabled(false);
        linearL = root.findViewById(R.id.linear_catal);
        linearL.setNestedScrollingEnabled(false);

        mPresenter = new MoviePresenter(this);
        if (fragment_name.equals("now_playing"))
            mPresenter.readMovies(getActivity(), Constants.BASE_URL_NOW_PLAYING, page);
        else if (fragment_name.equals("popular"))
            mPresenter.readMovies(getActivity(), Constants.BASE_URL_POPULAR, page);


        nest_scr.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                //When reach last item position, increase page size
                page++;
                if (fragment_name.equals("now_playing"))
                    mPresenter.readMovies(getActivity(), Constants.BASE_URL_NOW_PLAYING, page);
                else if (fragment_name.equals("popular"))
                    mPresenter.readMovies(getActivity(), Constants.BASE_URL_POPULAR, page);
            }
        });

        Funct.SwipeRefresh(getActivity(), root);

        return root;
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra("title", movieList.get(position).getTitle());
        intent.putExtra("description", movieList.get(position).getDescription());
        intent.putExtra("image", movieList.get(position).getImage());
        intent.putExtra("backDrop_image", movieList.get(position).getBackDrop_image());
        intent.putExtra("release_date", movieList.get(position).getRelease_date());
        intent.putExtra("average_vote", movieList.get(position).getAverage_vote());
        startActivity(intent);
    }


    @Override
    public void onMovieFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProcessStart() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
    }

    @Override
    public void onProcessEnd() {
        progressBar.setVisibility(View.GONE);
        progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
    }

    @Override
    public void onMovieRead(ArrayList<Movie> movies) {
        movieList = movies;
        adapter = new MoviesAdapter(getContext(), movies, MoviesCatalogue.this);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        recy.setAdapter(adapter);
    }

}
