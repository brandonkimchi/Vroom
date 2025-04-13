package com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vroomandroidapplicationv4.HomeActivity;
import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.ui.search.SearchProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Instructor> instructorList;
    private FragmentActivity activity;

    public CustomAdapter(List<Instructor> instructorList, FragmentActivity activity) {
        this.instructorList = instructorList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instructor instructor = instructorList.get(position);

        holder.instructorName.setText(instructor.getName());
        holder.instructorShortDescription.setText(instructor.getShortDescription());
        holder.instructorPrice.setText(instructor.getPrice());
        holder.instructorRating.setText(String.valueOf(instructor.getRating()));
        holder.instructorVehicleClass.setText(instructor.getVehicleClass());
        holder.instructorAddress.setText(instructor.getAddress());
        holder.instructorDrivingCenter.setText(String.valueOf(instructor.getDrivingCenter()));

        holder.profileImage.setImageResource(instructor.getImageResId());

        // 🔹 Clicking instructor opens their profile fragment
        holder.itemView.setOnClickListener(v -> changeToInstructorProfileFragment(instructor));
    }

    @Override
    public int getItemCount() {
        return instructorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView instructorName, instructorShortDescription, instructorPrice, instructorRating, instructorVehicleClass, instructorAddress, instructorDrivingCenter;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            instructorName = itemView.findViewById(R.id.reviewer_name);
            instructorShortDescription = itemView.findViewById(R.id.instructor_description);
            instructorPrice = itemView.findViewById(R.id.instructor_price);
            instructorRating = itemView.findViewById(R.id.instructor_rating);
            instructorVehicleClass = itemView.findViewById(R.id.instructor_vehicle_class);
            instructorAddress = itemView.findViewById(R.id.instructor_address);
            instructorDrivingCenter = itemView.findViewById(R.id.instructor_driving_center);
        }
    }

    private void changeToInstructorProfileFragment(Instructor instructor) {
        if (activity instanceof HomeActivity) {
            SearchProfileFragment fragment = new SearchProfileFragment();

            Log.d("DEBUG", "Instructor Name: " + instructor.getName());
            Log.d("DEBUG", "Unavailable Dates: " + instructor.getDatesUnavailable());

            Bundle bundle = new Bundle();
            bundle.putString("instructor_name", instructor.getName());
            bundle.putString("instructor_description", instructor.getFullDescription());
            bundle.putInt("instructor_image", instructor.getImageResId());
            bundle.putString("instructor_price", instructor.getPrice());
            bundle.putString("instructor_rating", String.valueOf(instructor.getRating()));
            bundle.putString("instructor_vehicle_class", instructor.getVehicleClass());
            bundle.putString("instructor_address", instructor.getAddress());
            bundle.putString("instructor_driving_center", instructor.getDrivingCenter());
            bundle.putParcelableArrayList("dates_unavailable", new ArrayList<>(instructor.getDatesUnavailable()));

            // 🔵 Handle reviews (convert to ArrayLists for Bundle)
            ArrayList<String> reviewerNames = new ArrayList<>(instructor.getReviews().keySet());
            ArrayList<Double> ratings = new ArrayList<>();
            ArrayList<String> reviewTexts = new ArrayList<>();

            for (String reviewer : reviewerNames) {
                Review review = instructor.getReviews().get(reviewer);
                if (review != null) {
                    ratings.add(review.getRating());
                    reviewTexts.add(review.getReview());
                }
            }

            bundle.putStringArrayList("reviewer_names", reviewerNames);
            bundle.putSerializable("ratings", ratings);
            bundle.putStringArrayList("review_texts", reviewTexts);

            fragment.setArguments(bundle);

            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
