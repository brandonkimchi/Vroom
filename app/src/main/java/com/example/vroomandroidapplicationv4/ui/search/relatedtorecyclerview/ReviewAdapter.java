package com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<String> reviewerNames;
    private List<Review> reviewList;

    public ReviewAdapter(List<String> reviewerNames, List<Review> reviewList) {
        this.reviewerNames = reviewerNames;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        String reviewerName = reviewerNames.get(position);

        holder.reviewerName.setText(reviewerName);
        holder.rating.setText(String.valueOf(review.getRating()));
        holder.description.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewerName;
        TextView rating;
        TextView description;
        ImageView starIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            rating = itemView.findViewById(R.id.instructor_rating);
            description = itemView.findViewById(R.id.instructor_description);
            starIcon = itemView.findViewById(R.id.star_icon);
        }
    }
}
