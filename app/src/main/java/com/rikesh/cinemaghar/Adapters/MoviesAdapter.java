package com.rikesh.cinemaghar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikesh.cinemaghar.Model.Movie;
import com.rikesh.cinemaghar.R;
import com.rikesh.cinemaghar.utils.Constants;
import com.rikesh.cinemaghar.utils.Funct;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    public Context context;
    public ArrayList<Movie> movies;
    public onMovieListener mOnMovieListener;

    public MoviesAdapter(Context context, ArrayList<Movie> movies, onMovieListener mOnMovieListener) {
        this.movies = movies;
        this.context = context;
        this.mOnMovieListener = mOnMovieListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, release_date, average_vote;
        public ImageView img;
        public MoviesAdapter.onMovieListener mListener;

        public MyViewHolder(View view, MoviesAdapter.onMovieListener mListener) {
            super(view);
            this.mListener = mListener;

            title = view.findViewById(R.id.single_item_movie_title);
            description = view.findViewById(R.id.single_item_movie_overview);
            release_date = view.findViewById(R.id.single_item_movie_release_date);
            average_vote = view.findViewById(R.id.single_item_movie_avg_vote);
            img = view.findViewById(R.id.single_item_movie_image);


        }

    }


    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);


        return new MoviesAdapter.MyViewHolder(itemView, mOnMovieListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder holder, int position) {
        holder.title.setText(movies.get(position).getTitle());
        holder.description.setText(movies.get(position).getDescription());
        holder.release_date.setText(movies.get(position).getRelease_date());
        holder.average_vote.setText(String.valueOf((movies.get(position).getAverage_vote())));
        Funct.setImage(context, holder.img, Constants.IMAGE_BASE_URL + movies.get(position).getImage());

        holder.itemView.setOnClickListener(view -> mOnMovieListener.onMovieClick(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface onMovieListener{
        void onMovieClick(int position);

    }

}
