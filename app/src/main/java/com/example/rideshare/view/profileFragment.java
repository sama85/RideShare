package com.example.rideshare.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rideshare.R;
import com.example.rideshare.databinding.FragmentProfileBinding;
import com.example.rideshare.databinding.FragmentRideDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileFragment extends Fragment {
    FragmentProfileBinding binding;
    private boolean isEditMode;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEditMode(false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        binding.btnSignout.setOnClickListener(v -> {
            mAuth.signOut();
            navigateToSignIn();
        });

        // Toggle edit mode when the Edit button is clicked
        binding.editBtn.setOnClickListener(v -> {
            isEditMode = !isEditMode;
            setEditMode(isEditMode);
        });

        // Handle save action
        binding.saveBtn.setOnClickListener(v -> {
            // Save changes and perform necessary actions
            // For example, update user profile data
            setEditMode(false); // Set back to display mode
        });

        // Handle cancel action
        binding.cancelBtn.setOnClickListener(v -> {
            // Discard changes and revert to display mode
            setEditMode(false);
            // Reset EditText fields with original values if needed
        });
    }

    // Method to toggle between display and edit modes
    private void setEditMode(boolean enabled) {
        if (enabled) {
            binding.nameEt.setVisibility(View.VISIBLE);
            binding.emailEt.setVisibility(View.VISIBLE);
            binding.phoneEt.setVisibility(View.VISIBLE);
            binding.name.setVisibility(View.GONE);
            binding.email.setVisibility(View.GONE);
            binding.phone.setVisibility(View.GONE);
            binding.editBtn.setVisibility(View.GONE);
            binding.saveBtn.setVisibility(View.VISIBLE);
            binding.cancelBtn.setVisibility(View.VISIBLE);
        } else {
            binding.nameEt.setVisibility(View.GONE);
            binding.emailEt.setVisibility(View.GONE);
            binding.phoneEt.setVisibility(View.GONE);
            binding.name.setVisibility(View.VISIBLE);
            binding.email.setVisibility(View.VISIBLE);
            binding.phone.setVisibility(View.VISIBLE);
            binding.editBtn.setVisibility(View.VISIBLE);
            binding.saveBtn.setVisibility(View.GONE);
            binding.cancelBtn.setVisibility(View.GONE);
        }
    }
    void navigateToSignIn(){
        Intent intent  = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

}