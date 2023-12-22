package com.example.rideshare.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.databinding.RideTrackingItemBinding;
import com.example.rideshare.models.Order;
import com.example.rideshare.models.Ride;

import java.util.ArrayList;
import java.util.List;

public class RidesTrackingAdapter extends RecyclerView.Adapter<RidesTrackingAdapter.RideViewHolder> {
    List<Ride> rides;
    List<Order> orders;

    OnTrackingClickListener listener;

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RideTrackingItemBinding binding = RideTrackingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RideViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        Ride rideItem = rides.get(position);
        Order order = this.orders.get(position);
        holder.bind(rideItem, order);
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    public void updateRides(List<Ride> rides, List<Order> orders) {
        this.rides = rides;
        this.orders = orders;
        if(orders.isEmpty())
            this.rides = new ArrayList<>();
        if(rides.isEmpty())
            this.orders = new ArrayList<>();
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

        public void bind(Ride rideItem, Order order) {
            binding.driverName.setText(rideItem.getDriverName());
            binding.source.setText(rideItem.getSrc());
            binding.destination.setText(rideItem.getDest());
            binding.date.setText(rideItem.getDate());
            binding.time.setText(rideItem.getTime());
            binding.costValue.setText(String.valueOf(rideItem.getCost()));
            binding.driverPhoneValue.setText(rideItem.getDriverPhone());
            if(rideItem.getStatus().equals("cancelled"))
                binding.statusValue.setText(rideItem.getStatus());
            else
                binding.statusValue.setText(order.getStatus());
            binding.carNoValue.setText(rideItem.getCarNumber());
            binding.paymentMethodValue.setText(order.getPaymentMethod());
            binding.cancelBtn.setOnClickListener(view ->{
                if(listener != null)
                    listener.onItemClick(order);
            });

            if(order.getStatus().equals("pending") && !rideItem.getStatus().equals("cancelled"))
                binding.cancelBtn.setVisibility(View.VISIBLE);
            else
                binding.cancelBtn.setVisibility(View.GONE);

            if(order.getStatus().equals("confirmed"))
                binding.statusValue.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.green));
            else if(order.getStatus().equals("declined"))
                binding.statusValue.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.red));
            else
                binding.statusValue.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black));
        }

    }

    public interface OnTrackingClickListener{
        void onItemClick(Order order);
    }
    public void setOnTrackingClickListener(OnTrackingClickListener listener){
        this.listener = listener;
    }
}