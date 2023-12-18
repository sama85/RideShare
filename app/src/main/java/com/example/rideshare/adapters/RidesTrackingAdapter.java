package com.example.rideshare.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.databinding.RideTrackingItemBinding;
import com.example.rideshare.models.Ride;

import java.util.List;

public class RidesTrackingAdapter extends RecyclerView.Adapter<RidesTrackingAdapter.RideViewHolder> {
    List<Ride> rides;

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RideTrackingItemBinding binding = RideTrackingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RideViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        Ride rideItem = rides.get(position);
        holder.bind(rideItem);
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    public void updateRides(List<Ride> rides) {
        this.rides = rides;
        Log.i("ride", "update ride called");
        // to redraw recycler view
        notifyDataSetChanged();
    }

    public class RideViewHolder extends RecyclerView.ViewHolder {
        RideTrackingItemBinding binding;

        public RideViewHolder(RideTrackingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ride rideItem) {
            binding.driverName.setText(rideItem.getDriverName());
            binding.source.setText(rideItem.getSrc());
            binding.destination.setText(rideItem.getDest());
            binding.date.setText(rideItem.getDate());
            binding.time.setText(rideItem.getTime());
            binding.costValue.setText(String.valueOf(rideItem.getCost()));
            binding.driverPhoneValue.setText(rideItem.getDriverPhone());
            binding.statusValue.setText(rideItem.getStatus());
            binding.carNoValue.setText(rideItem.getCarNumber());
            binding.paymentMethodValue.setText(rideItem.getPaymentMethod());

            if(rideItem.getStatus() == "confirmed")
                binding.statusValue.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.green));
            else if(rideItem.getStatus() == "declined")
                binding.statusValue.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.red));
            else
                binding.statusValue.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black));
        }

    }
}