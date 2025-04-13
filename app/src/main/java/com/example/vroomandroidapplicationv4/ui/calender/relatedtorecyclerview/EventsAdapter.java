package com.example.vroomandroidapplicationv4.ui.calender.relatedtorecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.R;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private List<Event> events;

    public EventsAdapter(List<Event> events) {
        this.events = events;
    }

    public void updateData(List<Event> newEvents) {
        this.events = newEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item4, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.titleTextView.setText(event.getTitle());
        holder.datetimeTextView.setText(event.getDateTime());
        holder.descriptionTextView.setText(event.getDescription());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, datetimeTextView, descriptionTextView;
        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvEventTitle);
            datetimeTextView = itemView.findViewById(R.id.tvEventDateTime);
            descriptionTextView = itemView.findViewById(R.id.tvEventDescription);
        }
    }
}