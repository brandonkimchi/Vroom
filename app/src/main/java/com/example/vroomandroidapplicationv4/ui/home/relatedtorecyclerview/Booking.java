package com.example.vroomandroidapplicationv4.ui.home.relatedtorecyclerview;

public class Booking {
    public String instructor;
    public int day, month, year;
    public String time;
    public String vehicleClass;
    public String address;
    public String drivingCenter;

    public Booking(String instructor, int day, int month, int year, String time, String vehicleClass, String address, String drivingCenter) {
        this.instructor = instructor;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.vehicleClass = vehicleClass;
        this.address = address;
        this.drivingCenter = drivingCenter;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getTime() {
        return time;
    }
}
