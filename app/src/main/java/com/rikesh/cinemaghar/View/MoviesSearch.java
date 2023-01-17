package com.rikesh.cinemaghar.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.rikesh.cinemaghar.Adapters.MoviesAdapter;
import com.rikesh.cinemaghar.Core.MovieContract;
import com.rikesh.cinemaghar.Core.MoviePresenter;
import com.rikesh.cinemaghar.Model.Movie;
import com.rikesh.cinemaghar.R;
import com.rikesh.cinemaghar.utils.Constants;
import com.rikesh.cinemaghar.utils.Funct;

import java.util.ArrayList;
import java.util.Objects;

public class MoviesSearch extends AppCompatActivity implements MoviesAdapter.onMovieListener, MovieContract.View{
    ArrayList<Movie> movieList;
    RecyclerView recy_search;
    MoviesAdapter adapter;
    public MoviePresenter mPresenter;
    ProgressBar progressBar;

    NestedScrollView nest_scr;
    LinearLayout lin;
    int page = 1;
    String search_query;

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search_query = query;
                searchView.clearFocus();
                if (movieList != null)
                    movieList.clear();
                mPresenter.searchMovies(getApplicationContext(), Constants.BASE_URL_SEARCH, page, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Funct.isNetworkConnected(this)) {
            setContentView(R.layout.no_internet);
            LinearLayout try_again = findViewById(R.id.try_again);

            try_again.setOnClickListener(v -> Funct.RefreshActivity(this));
        }
        else
            setContentView(R.layout.movies_search);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recy_search = findViewById(R.id.recy_search);
        progressBar = findViewById(R.id.progressBar);
        nest_scr = findViewById(R.id.scroll_view);
        nest_scr.setNestedScrollingEnabled(false);
        lin = findViewById(R.id.linear_search);
        lin.setNestedScrollingEnabled(false);


        mPresenter = new MoviePresenter(this);

        nest_scr.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                //When reach last item position, increase page size
                page++;
                if (search_query != null)
                    mPresenter.searchMovies(getApplicationContext(), Constants.BASE_URL_SEARCH, page, search_query);
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProcessStart() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }

    @Override
    public void onProcessEnd() {
        progressBar.setVisibility(View.GONE);
        progressBar.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
    }

    @Override
    public void onMovieRead(ArrayList<Movie> movies) {
        movieList = movies;
        adapter = new MoviesAdapter(this, movies, MoviesSearch.this);
        recy_search.setLayoutManager(new LinearLayoutManager(this));
        recy_search.setAdapter(adapter);
    }
}
