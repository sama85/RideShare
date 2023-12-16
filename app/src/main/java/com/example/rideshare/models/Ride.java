package com.example.rideshare.models;

public class Ride {
    String src;
    String dest;
    String date;
    String time;
    String driverId;
    Long cost;

    public Ride (String source, String destination, String date, String time, String driverId, Long cost) {
        this.src = source;
        this.dest = destination;
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.driverId = driverId;
    }

    public Ride() {

    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
