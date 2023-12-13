package com.example.rideshare.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.rideshare.databinding.ActivitySignUpBinding;
import com.example.rideshare.viewModels.SignUpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    SignUpViewModel viewModel;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        viewModel.signUpSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if(success){
                    Toast.makeText(getApplicationContext(), "Account Created!",Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(SignUpActivity.this, RiderActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Authentication Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name, phone;
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();
                name = binding.name.getText().toString();
                phone = binding.phone.getText().toString();
                
                if(email.isEmpty() || name.isEmpty() || password.isEmpty() || phone.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewModel.signUp(email, password, name, phone);
            }
        });

    }
}
