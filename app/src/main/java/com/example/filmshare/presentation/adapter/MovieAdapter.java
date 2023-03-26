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
    public void onBindViewHolder(@NonNull MovieViewHolder mealViewHolder, int position) {
        Movie currentMeal = movies.get(position);
        mealViewHolder.movieTitle.setText(currentMeal.getTitle());

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



        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);

            itemView.setOnClickListener(this);
        }



        public void onClick(View view) {
            Movie currentMeal = movies.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), MovieActivity.class);
            intent.putExtra("name", currentMeal.getTitle());

            view.getContext().startActivity(intent);

        }


    }
}
