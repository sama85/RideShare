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

import com.example.rideshare.databinding.FragmentRidesListBinding;
import com.example.rideshare.viewModels.RidesListViewModel;
import com.example.rideshare.viewModels.RidesTrackingViewModel;

import java.util.List;

public class RidesTrackingFragment extends Fragment {
    FragmentRidesListBinding binding;
    RidesTrackingViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRidesListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RidesTrackingViewModel.class);
        viewModel.getRideIds().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> ids) {
                if(!ids.isEmpty()){
                    for (String id : ids) {
                        viewModel.fetchRides(ids);
                    }
                }
            }
        });
    }
}
