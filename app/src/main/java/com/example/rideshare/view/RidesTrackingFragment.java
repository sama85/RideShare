package com.example.rideshare.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.rideshare.adapters.RidesTrackingAdapter;
import com.example.rideshare.databinding.FragmentRidesTrackingBinding;
import com.example.rideshare.models.Order;
import com.example.rideshare.models.Ride;
import com.example.rideshare.viewModels.RidesTrackingViewModel;

import java.util.List;

public class RidesTrackingFragment extends Fragment {
    FragmentRidesTrackingBinding binding;
    RidesTrackingViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRidesTrackingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RidesTrackingViewModel.class);
        RidesTrackingAdapter adapter = new RidesTrackingAdapter();
        RidesTrackingAdapter.OnTrackingClickListener listener = new RidesTrackingAdapter.OnTrackingClickListener() {
            @Override
            public void onItemClick(Order order) {
                viewModel.cancelOrder(order);
            }
        };
        adapter.setOnTrackingClickListener(listener);
        binding.ridesTrackingList.setAdapter(adapter);
        viewModel.getRideIds().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> ids) {
                if(!ids.isEmpty()){
                    Log.i("tracking", String.valueOf(ids.size()));
                    viewModel.fetchRides(ids);
                }
            }
        });
        viewModel.getRides().observe(getViewLifecycleOwner(), new Observer<List<Ride>>() {
            @Override
            public void onChanged(List<Ride> rides) {
                if(rides != null) {
                    adapter.updateRides(rides, viewModel.orders);
                }
            }
        });
    }
}
