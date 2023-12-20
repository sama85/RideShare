package com.example.rideshare.models;

public class Order {
    private String paymentMethod;
    private String status;
    private String date;
    private String time;
    private String pushId;

    public Order(String paymentMethod, String status, String date, String time, String pushId) {
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.date = date;
        this.time = time;
        this.pushId = pushId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
