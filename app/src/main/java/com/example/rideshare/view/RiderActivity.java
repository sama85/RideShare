package com.example.rideshare.view;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiderMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        checkAuth();
        navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.main_frame);
        navController = navHost.getNavController();
        setupWithNavController(binding.bottomNavBar, navController);

        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                navigateToSignIn();
            }
        });
    }
    void checkAuth(){
        if (user == null)
            navigateToSignIn();
    }
    void navigateToSignIn(){
        Intent intent  = new Intent(RiderActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}

