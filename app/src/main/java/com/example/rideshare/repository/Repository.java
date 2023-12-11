package com.example.rideshare.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rideshare.room.User;
import com.example.rideshare.room.UserDao;
import com.example.rideshare.room.UserDatabase;

public class Repository {
    private UserDao userDao;
    LiveData<User> user;

    public Repository(Application application){
        UserDatabase userDB = UserDatabase.getInstance(application);
        this.userDao = userDB.userDao();
        // TODO: prepopulate db with this user
        user = userDao.getUser("sama@google.com");
    }

    /** Room runs db operations that return livedata on a bg thread by default */
    public LiveData<User> getUser(String email){
        return user;
    }

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
