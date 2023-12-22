package com.example.rideshare.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rideshare.databinding.FragmentPaymentBinding;
import com.example.rideshare.databinding.FragmentRideDetailsBinding;

public class PaymentFragment extends Fragment {
    private FragmentPaymentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardName = binding.editTextCardholderName.getText().toString();
                String cardNumber = binding.editTextCardNumber.getText().toString();
                String expiry = binding.editTextExpiryDate.getText().toString();
                String code = binding.editTextSecurityCode.getText().toString();

                if(cardName.isEmpty() || cardNumber.isEmpty() || expiry.isEmpty() || code.isEmpty())
                    Toast.makeText(requireActivity(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(requireActivity(), "Payment Completed!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(PaymentFragmentDirections.actionPaymentFragmentToRidesTrackingFragment());
                }
            }
        });
    }
}
