package com.example.rideshare.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rideshare.R;
import com.example.rideshare.adapters.RidesListAdapter;
import com.example.rideshare.databinding.FragmentRidesListBinding;
import com.example.rideshare.models.Ride;
import com.example.rideshare.viewModels.RidesListViewModel;

public class RidesListFragment extends Fragment {
    FragmentRidesListBinding binding;
    RidesListViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRidesListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RidesListViewModel.class);
        RidesListAdapter adapter = new RidesListAdapter(viewModel.getRides());
        binding.ridesList.setAdapter(adapter);
        return binding.getRoot();
    }
}
