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
        populateRideDetails(ride);
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton radioButton = view.findViewById(id);
                if (radioButton != null) {
                    String selectedOption = radioButton.getText().toString();
                    ride.setPaymentMethod(selectedOption);
                }
            }
        });

        binding.proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** add ride to order/request collection (rider id, ride id)
                 *    STATUSSS?
                 * */

                // navigate to tracking history
            }
        });
    }

    private void populateRideDetails(Ride ride) {
        binding.driverName.setText(ride.getDriverName());
        binding.srcValue.setText(ride.getSrc());
        binding.destValue.setText(ride.getDest());
        binding.costValue.setText(String.valueOf(ride.getCost()));
        binding.driverPhoneValue.setText(ride.getDriverPhone());
        // TODO: handle the car number and display it
//        binding.vehicleNoValue.setText(ride);
    }
}
