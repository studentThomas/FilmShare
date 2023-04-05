package com.example.filmshare.presentation.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmshare.R;
import com.example.filmshare.domain.Review;
import com.example.filmshare.presentation.MovieActivity;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review currentReview = reviews.get(position);
        holder.author.setText(currentReview.getAuthor());
        holder.content.setText(currentReview.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView author;
        private TextView content;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.review_author);
            content = itemView.findViewById(R.id.review_content);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Review currentReview = reviews.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), MovieActivity.class);
            intent.putExtra("author", currentReview.getAuthor());
            intent.putExtra("content", currentReview.getContent());

            view.getContext().startActivity(intent);
        }
    }
}
