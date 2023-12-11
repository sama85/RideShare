package com.example.rideshare.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.rideshare.R;
import com.example.rideshare.adapters.RidesListAdapter;
import com.example.rideshare.databinding.FragmentRidesListBinding;
import com.example.rideshare.models.Ride;
import com.example.rideshare.viewModels.RidesListViewModel;

import java.util.List;

public class RidesListFragment extends Fragment {
    FragmentRidesListBinding binding;
    RidesListViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRidesListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RidesListViewModel.class);
        RidesListAdapter adapter = new RidesListAdapter();
        binding.ridesList.setAdapter(adapter);
        viewModel.getRides().observe(getViewLifecycleOwner(), new Observer<List<Ride>>() {
            @Override
            public void onChanged(List<Ride> rides) {
                if(rides != null)
                    adapter.updateRides(rides);
            }
        });

    }
}
