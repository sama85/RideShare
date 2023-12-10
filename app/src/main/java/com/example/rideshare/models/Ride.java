package com.example.rideshare.models;

public class Ride {
    String driverName;
    String source;
    String destination;
    String date;
    String time;
    double cost;

    public Ride(String driverName, String source, String destination, String date, String departTime, double cost) {
        this.driverName = driverName;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.time = departTime;
        this.cost = cost;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
