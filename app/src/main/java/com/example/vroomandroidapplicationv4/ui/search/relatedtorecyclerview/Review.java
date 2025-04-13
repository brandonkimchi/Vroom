package com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview;

public class Review {
    private double rating;
    private String review;

    public Review() {} // Required for Firebase

    public Review(double rating, String review) {
        this.rating = rating;
        this.review = review;
    }

    public double getRating() { return rating; }
    public String getReview() { return review; }
}