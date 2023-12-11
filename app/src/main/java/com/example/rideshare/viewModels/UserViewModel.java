package com.example.rideshare.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rideshare.repository.Repository;
import com.example.rideshare.room.User;

public class UserViewModel extends AndroidViewModel{
    private Repository repository;
    private LiveData<User> user;
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        user = repository.getUser("sama@google.com");
    }
    public LiveData<User> getUser() {
        return user;
    }
}
