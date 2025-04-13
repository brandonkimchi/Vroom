package com.example.vroomandroidapplicationv4;

import java.util.HashMap;
import java.util.List;

public class User {
    private int id;
    private String name, password, address;
    private List<HashMap> bookings; // Now stores CalendarDay objects directly

    public User(int id, String name, String address, String password, List<HashMap> bookings) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.bookings = bookings;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public List<HashMap> getBookings() { return bookings; }

    @Override
    public String toString() {
        return "User {" +
                "ID='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}