package com.example.rideshare.view;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;

import com.example.rideshare.R;
import com.example.rideshare.databinding.ActivityRiderMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RiderActivity extends AppCompatActivity {
    ActivityRiderMainBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser user;
    NavHost navHost;
    NavController navController;
    private static String userEmail;


    public static String getUserEmail() {
        return userEmail;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiderMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userEmail = getIntent().getStringExtra("userEmail");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        checkAuth();
        navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.main_frame);
        navController = navHost.getNavController();
        setupWithNavController(binding.bottomNavBar, navController);
        //Repository rep = new Repository(getApplication());
        //rep.setUpUser("sama@google.com");
    }
    void checkAuth(){
        if (user == null)
            navigateToSignIn();
        else userEmail = user.getEmail();
    }
    void navigateToSignIn(){
        Intent intent  = new Intent(RiderActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}

