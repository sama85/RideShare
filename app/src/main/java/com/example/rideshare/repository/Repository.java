package com.example.rideshare.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rideshare.room.User;
import com.example.rideshare.room.UserDao;
import com.example.rideshare.room.UserDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repository {
    private UserDao userDao;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference ridesRef;


    public Repository(Application application){
        UserDatabase userDB = UserDatabase.getInstance(application);
        this.userDao = userDB.userDao();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        ridesRef = FirebaseDatabase.getInstance("https://rideshareapp-authentication-default-rtdb.europe-west1.firebasedatabase.app/").getReference("rides");
    }

    /** Room runs db operations that return livedata on a bg thread by default */
    public LiveData<User> getUser(String email){
      return userDao.getUser(email);
    }

    public void insert(User user){
        //insert to FB and Room

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
