package com.example.rideshare.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rideshare.models.Ride;
import com.example.rideshare.room.User;
import com.example.rideshare.room.UserDao;
import com.example.rideshare.room.UserDatabase;
import com.example.rideshare.view.RiderActivity;

public class Repository {
    private UserDao userDao;
    LiveData<User> user = new MutableLiveData<>();

    public Repository(Application application){
        UserDatabase userDB = UserDatabase.getInstance(application);
        this.userDao = userDB.userDao();
        user = userDao.getUser(RiderActivity.getUserEmail());
    }

    /** Room runs db operations that return livedata on a bg thread by default */
    public LiveData<User> getUser(){
        return user;
    }

//    public void setUpUser(String email){
//        UserDatabase.databaseWriteExecutor.execute(() -> {
//            user.postValue(userDao.getUser(email).getValue());
//        });
//    }

    public void insert(User user){
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }
    public void update(User user){
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDao.update(user);
        });
    }
}
