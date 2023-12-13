package com.example.rideshare.viewModels;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rideshare.repository.Repository;
import com.example.rideshare.room.User;
import com.example.rideshare.view.RiderActivity;
import com.example.rideshare.view.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpViewModel extends AndroidViewModel {
    private Repository repository;
    FirebaseAuth mAuth;
    public MutableLiveData<Boolean> signUpSuccess = new MutableLiveData<>();
    public SignUpViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String email, String password, String name, String phone){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            signUpSuccess.postValue(true);
                            User user = new User(email, name, phone);
                            repository.insert(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            signUpSuccess.postValue(false);
                        }
                    }
                });
    }
}
