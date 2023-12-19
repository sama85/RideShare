package com.example.rideshare.models;

public class Order {
    private String paymentMethod;
    private String status;

    public Order(String paymentMethod, String status) {
        this.paymentMethod = paymentMethod;
        this.status = status;
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
}
