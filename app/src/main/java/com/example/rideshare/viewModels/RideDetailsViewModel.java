package com.example.rideshare.viewModels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rideshare.R;
import com.example.rideshare.models.Ride;
import com.example.rideshare.repository.Repository;
import com.example.rideshare.room.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RideDetailsViewModel extends AndroidViewModel {
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference ordersRef;
    private DatabaseReference ridesRef;
    private Repository repository;
    public LiveData<User> rider;
    public RideDetailsViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        repository = new Repository(application);
        rider = repository.getUser(firebaseUser.getEmail());
        ordersRef = FirebaseDatabase.getInstance("https://rideshareapp-authentication-default-rtdb.europe-west1.firebasedatabase.app/").getReference("orders");
        ridesRef = FirebaseDatabase.getInstance("https://rideshareapp-authentication-default-rtdb.europe-west1.firebasedatabase.app/").getReference("rides");
    }

    public void addOrder(Ride ride, User rider) {

        //update ride payment option
        ridesRef.child(ride.getPushId()).child("paymentMethod").setValue(ride.getPaymentMethod());

        //add ride to orders with rideId and userId
        String key = ordersRef.push().getKey();
        ordersRef.child(key).child("userId").setValue(firebaseUser.getUid());
        ordersRef.child(key).child("riderName").setValue(rider.getName());
        ordersRef.child(key).child("status").setValue("pending");
        ordersRef.child(key).child("driverId").setValue(ride.getDriverId());
        ordersRef.child(key).child("rideId").setValue(ride.getPushId()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplication().getApplicationContext(), "Order created successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
