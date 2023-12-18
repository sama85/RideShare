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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

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
        RidesListAdapter.OnItemClickListener rideListener = new RidesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ride ride) {
                // navigate to ride details page
                NavDirections action = RidesListFragmentDirections.actionRidesListFragmentToRideDetailsFragment(ride);
                Navigation.findNavController(view).navigate(action);
            }
        };
        adapter.setOnItemClickListener(rideListener);
        binding.ridesList.setAdapter(adapter);
        viewModel.getRides().observe(getViewLifecycleOwner(), new Observer<List<Ride>>() {
            @Override
            public void onChanged(List<Ride> rides) {
                if(rides != null) {
                    //Log.i("ride", rides.get(0).getSrc());
                    adapter.updateRides(rides);
                }
            }
        });
        String src = RidesListFragmentArgs.fromBundle(getArguments()).getSrc();
        if(src == null){
            viewModel.fetchRides();
        }
        else{
            String dest = RidesListFragmentArgs.fromBundle(getArguments()).getDest();
            String date = RidesListFragmentArgs.fromBundle(getArguments()).getDate();
            String time = RidesListFragmentArgs.fromBundle(getArguments()).getTime();
            viewModel.fetchRides(src, dest, date, time);
        }

    }
}
