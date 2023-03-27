package com.example.filmshare.presentation.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmshare.domain.Movie;
import com.example.filmshare.R;
import com.example.filmshare.presentation.MovieActivity;


import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie currentMovie = movies.get(position);
        movieViewHolder.movieTitle.setText(currentMovie.getTitle());
        movieViewHolder.movieOverview.setText(currentMovie.getOverview());


        String imageUrl = "https://image.tmdb.org/t/p/w500" + currentMovie.getBackdropPath();

        Glide.with(movieViewHolder.movieImage.getContext())
                .load(imageUrl)
                .into(movieViewHolder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView movieTitle;
        private TextView movieOverview;
        private ImageView movieImage;



        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieOverview = itemView.findViewById(R.id.movie_overview);
            movieImage = itemView.findViewById(R.id.movie_image);

            itemView.setOnClickListener(this);
        }



        public void onClick(View view) {
            Movie currentMeal = movies.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), MovieActivity.class);
            intent.putExtra("title", currentMeal.getTitle());

            view.getContext().startActivity(intent);

        }


    }
}
