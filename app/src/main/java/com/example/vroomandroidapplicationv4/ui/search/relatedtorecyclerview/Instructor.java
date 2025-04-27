package com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview;

import com.example.vroomandroidapplicationv4.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;
import java.util.Map;

public class Instructor extends User {
    private String shortDescription, fullDescription, price, vehicleClass, drivingCenter;
    private double rating;
    private int imageResId;
    private List<CalendarDay> datesUnavailable;
    private Map<String, Review> reviews;

    // 🔥 Constructor matching your creation order
    public Instructor(String name,
                      String shortDescription,
                      String fullDescription,
                      String price,
                      double rating,
                      int imageResId,
                      String vehicleClass,
                      String address,
                      String drivingCenter,
                      List<CalendarDay> datesUnavailable,
                      Map<String, Review> reviews) {
        super(name, address); // Initialize name and address from User
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.price = price;
        this.rating = rating;
        this.imageResId = imageResId;
        this.vehicleClass = vehicleClass;
        this.drivingCenter = drivingCenter;
        this.datesUnavailable = datesUnavailable;
        this.reviews = reviews;
    }

    // ✅ Getters
    public String getShortDescription() { return shortDescription; }
    public String getFullDescription() { return fullDescription; }
    public String getPrice() { return price; }
    public double getRating() { return rating; }
    public int getImageResId() { return imageResId; }
    public String getVehicleClass() { return vehicleClass; }
    public String getDrivingCenter() { return drivingCenter; }
    public List<CalendarDay> getDatesUnavailable() { return datesUnavailable; }
    public Map<String, Review> getReviews() { return reviews; }
}
