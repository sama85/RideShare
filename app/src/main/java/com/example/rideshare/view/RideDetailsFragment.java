package com.example.rideshare.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.rideshare.R;
import com.example.rideshare.databinding.FragmentRideDetailsBinding;
import com.example.rideshare.models.Ride;
import com.example.rideshare.room.User;
import com.example.rideshare.viewModels.RideDetailsViewModel;
import com.example.rideshare.viewModels.RidesListViewModel;

public class RideDetailsFragment extends Fragment {
    private FragmentRideDetailsBinding binding;
    private RideDetailsViewModel viewModel;
    private String paymentMethod;
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
        viewModel = new ViewModelProvider(this).get(RideDetailsViewModel.class);

        // receive ride from rides list fragment and display it
        Ride ride = RideDetailsFragmentArgs.fromBundle(getArguments()).getRide();
        populateRideDetails(ride);
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton radioButton = view.findViewById(id);
                if (radioButton != null) {
                    String selectedOption = radioButton.getText().toString();
                    paymentMethod = selectedOption;
                }
            }
        });

        binding.proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.rider.observe(requireActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user != null) {
                            viewModel.addOrder(ride, user, paymentMethod);
                            if(paymentMethod.equals("Credit Card"))
                                Navigation.findNavController(view).navigate(RideDetailsFragmentDirections.actionRideDetailsFragmentToPaymentFragment());
                           else
                               Navigation.findNavController(view).navigate(RideDetailsFragmentDirections.actionRideDetailsFragmentToRidesTrackingFragment());
                        }
                    }
                });

            }
        });
    }

    private void populateRideDetails(Ride ride) {
        binding.driverName.setText(ride.getDriverName());
        binding.srcValue.setText(ride.getSrc());
        binding.destValue.setText(ride.getDest());
        binding.costValue.setText(String.valueOf(ride.getCost()));
        binding.driverPhoneValue.setText(ride.getDriverPhone());
        binding.vehicleNoValue.setText(ride.getCarNumber());
    }
}
