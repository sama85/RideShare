package com.example.rideshare.room;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String phone;


    public User(@NonNull String email, @NonNull String name, @NonNull String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    @NonNull
    public String getName() {
        return name;
    }
    @NonNull
    public String getEmail() {
        return email;
    }
    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }
}
