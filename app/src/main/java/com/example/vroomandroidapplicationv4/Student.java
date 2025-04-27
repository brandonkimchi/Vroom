package com.example.vroomandroidapplicationv4;

import java.util.HashMap;
import java.util.List;

public class Student extends User {
    private int id;
    private String password;
    private List<HashMap> bookings;

    public Student(int id, String name, String address, String password, List<HashMap> bookings) {
        super(name, address); // Initialize shared fields
        this.id = id;
        this.password = password;
        this.bookings = bookings;
    }

    public int getId() { return id; }
    public String getPassword() { return password; }
    public List<HashMap> getBookings() { return bookings; }
}


