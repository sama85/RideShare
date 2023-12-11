package com.example.rideshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.databinding.RideItemBinding;
import com.example.rideshare.models.Ride;

import java.util.ArrayList;
import java.util.List;

public class RidesListAdapter extends RecyclerView.Adapter<RidesListAdapter.RideViewHolder>{
    List<Ride> rides;

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RideItemBinding binding = RideItemBinding.inflate(LayoutInflater.from(parent.getContext()));
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

    public void updateRides(List<Ride> rides){
        this.rides = rides;
        // to redraw recycler view
        notifyDataSetChanged();
    }

    public class RideViewHolder extends RecyclerView.ViewHolder{
        RideItemBinding binding;
        public RideViewHolder(RideItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Ride rideItem){
            binding.driverName.setText(rideItem.getDriverName());
            binding.source.setText(rideItem.getSource());
            binding.destination.setText(rideItem.getDestination());
            binding.date.setText(rideItem.getDate());
            binding.time.setText(rideItem.getTime());
            binding.costValue.setText(String.valueOf(rideItem.getCost()));
        }
    }
}

