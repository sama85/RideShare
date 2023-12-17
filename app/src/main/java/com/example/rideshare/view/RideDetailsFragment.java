package com.example.rideshare.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rideshare.R;
import com.example.rideshare.databinding.FragmentRideDetailsBinding;
import com.example.rideshare.models.Ride;

public class RideDetailsFragment extends Fragment {

    FragmentRideDetailsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRideDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Ride ride = RideDetailsFragmentArgs.fromBundle(getArguments()).getRide();
        Log.i("ride", "ride src is " + ride.getSrc());
    }
}
