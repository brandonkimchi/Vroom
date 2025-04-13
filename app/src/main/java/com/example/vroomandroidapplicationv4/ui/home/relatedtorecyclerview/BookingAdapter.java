package com.example.vroomandroidapplicationv4.ui.home.relatedtorecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vroomandroidapplicationv4.R;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Booking> bookings;

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item3, parent, false); // replace with your item XML filename
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookings.get(position);

        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        int monthIndex = booking.month - 1;
        String monthName = (monthIndex >= 0 && monthIndex < 12) ? monthNames[monthIndex] : "Unknown";

        String dateTime = String.format("%s %d %d, %s", monthName, booking.day, booking.year, booking.time);
        holder.reviewerName.setText(dateTime);
        holder.reviewerName.setText(dateTime);

        holder.instructorDesc.setText(booking.drivingCenter);
        holder.instructorDesc2.setText("Instructor " + booking.instructor + " (Class " + booking.vehicleClass + ")");
        holder.instructorDesc3.setText("Pickup location: " + booking.address);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewerName, instructorDesc, instructorDesc2, instructorDesc3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            instructorDesc = itemView.findViewById(R.id.instructor_description);
            instructorDesc2 = itemView.findViewById(R.id.instructor_description2);
            instructorDesc3 = itemView.findViewById(R.id.instructor_description3);
        }
    }
}
