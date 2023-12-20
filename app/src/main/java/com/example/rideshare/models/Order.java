package com.example.rideshare.models;

public class Order {
    private String paymentMethod;
    private String status;
    private String pushId;

    public Order(String paymentMethod, String status, String pushId) {
        this.paymentMethod = paymentMethod;
        this.status = status;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
