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
        movieViewHolder.title.setText(currentMovie.getTitle());
        movieViewHolder.overview.setText(currentMovie.getOverview());


        String imageUrl = "https://image.tmdb.org/t/p/w500" + currentMovie.getBackdropPath();

        Glide.with(movieViewHolder.backdrop.getContext())
                .load(imageUrl)
                .into(movieViewHolder.backdrop);
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
        private TextView title;
        private TextView overview;
//        private TextView language;
//        private TextView runtime;
//        private TextView budget;
//        private TextView releaseDate;
//        private TextView revenue;
//        private TextView popularity;
//        private TextView status;
//        private TextView tagline;
//        private TextView voteAverage;
//        private TextView voteCount;
        private ImageView backdrop;




        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.movie_overview);
//            language = itemView.findViewById(R.id.movie_language);
//            runtime = itemView.findViewById(R.id.movie_runtime);
//            budget = itemView.findViewById(R.id.movie_budget);
//            releaseDate = itemView.findViewById(R.id.movie_releaseDate);
//            revenue = itemView.findViewById(R.id.movie_revenue);
//            popularity = itemView.findViewById(R.id.movie_popularity);
//            status = itemView.findViewById(R.id.movie_status);
//            tagline = itemView.findViewById(R.id.movie_tagline);
//            voteAverage = itemView.findViewById(R.id.movie_voteAverage);
//            voteCount = itemView.findViewById(R.id.movie_voteCount);
            backdrop = itemView.findViewById(R.id.movie_image);



            itemView.setOnClickListener(this);
        }



        public void onClick(View view) {
            Movie currentMeal = movies.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), MovieActivity.class);
            intent.putExtra("title", currentMeal.getTitle());
            intent.putExtra("overview", currentMeal.getOverview());
            intent.putExtra("language", currentMeal.getLanguage());
            intent.putExtra("runtime", currentMeal.getRuntime());
            intent.putExtra("budget", currentMeal.getBudget());
            intent.putExtra("releaseDate", currentMeal.getReleaseDate());
            intent.putExtra("revenue", currentMeal.getRevenue());
            intent.putExtra("popularity", currentMeal.getPopularity());
            intent.putExtra("status", currentMeal.getStatus());
            intent.putExtra("tagline", currentMeal.getTagline());
            intent.putExtra("voteAverage", currentMeal.getVoteAverage());
            intent.putExtra("voteCount", currentMeal.getVoteCount());
            intent.putExtra("backdrop", currentMeal.getBackdropPath());

            view.getContext().startActivity(intent);

        }


    }
}
