package com.example.rideshare.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.rideshare.databinding.ActivitySignUpBinding;
import com.example.rideshare.viewModels.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    SignUpViewModel viewModel;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        viewModel.signUpEmail.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String email) {
                if(email != null){
                    Toast.makeText(getApplicationContext(), "Account Created!",Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(SignUpActivity.this, RiderActivity.class);
                    intent.putExtra("userEmail", email);
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
