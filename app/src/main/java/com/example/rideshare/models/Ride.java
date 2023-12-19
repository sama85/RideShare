package com.example.rideshare.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Ride implements Parcelable {
    String pushId;
    String src;
    String dest;
    String date;
    String time;
    String driverId;
    String driverName;
    String driverPhone;
    String carNumber;
    String status;
    String requestStatus;
    Long cost;
    Integer capacity;


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

    protected Ride(Parcel in) {
        pushId = in.readString();
        src = in.readString();
        dest = in.readString();
        date = in.readString();
        time = in.readString();
        driverId = in.readString();
        driverName = in.readString();
        driverPhone = in.readString();
        carNumber = in.readString();
        status = in.readString();
        requestStatus = in.readString();
        cost = in.readLong();
        capacity = in.readInt();
    }

    public static final Creator<Ride> CREATOR = new Creator<Ride>() {
        @Override
        public Ride createFromParcel(Parcel in) {
            return new Ride(in);
        }

        @Override
        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
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
    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeString(pushId);
        dest.writeString(src);
        dest.writeString(this.dest);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(driverId);
        dest.writeString(driverName);
        dest.writeString(driverPhone);
        dest.writeString(carNumber);
        dest.writeString(status);
        dest.writeString(requestStatus);
        dest.writeLong(cost);
        dest.writeInt(capacity);

    }

}
