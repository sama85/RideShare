package com.example.rideshare.viewModels;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rideshare.repository.Repository;
import com.example.rideshare.room.User;
import com.example.rideshare.view.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel extends AndroidViewModel{
    private Repository repository;
    private LiveData<User> user;
    public MutableLiveData<Boolean> isEditMode = new MutableLiveData<>();
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        Log.i("profile", "email :" + firebaseUser.getEmail());
        user = repository.getUser(firebaseUser.getEmail());
        isEditMode.setValue(false);
    }
    public LiveData<User> getUser() {
        return user;
    }

    public void handle_signout(){
        mAuth.signOut();
    }
    public void handle_edit_click(){
        isEditMode.setValue(true);
    }
    public void handle_save_click(){
        isEditMode.setValue(false);
    }

    public void handle_cancel_click(){
        isEditMode.setValue(false);
    }

}
