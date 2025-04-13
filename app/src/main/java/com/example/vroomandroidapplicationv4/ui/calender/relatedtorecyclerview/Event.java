package com.example.vroomandroidapplicationv4.ui.calender.relatedtorecyclerview;

public class Event {
    private String title;
    private String datetime;
    private String description;

    public Event(String title, String time, String description) {
        this.title = title;
        this.datetime = time;
        this.description = description;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDateTime() { return datetime; }
    public String getDescription() { return description; }
}