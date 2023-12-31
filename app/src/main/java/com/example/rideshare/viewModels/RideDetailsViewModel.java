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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public void addOrder(Ride ride, User rider, String paymentMethod) {

        // get current date and time of request
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String formattedDate = currentDate.format(dateFormatter);

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        String formattedTime = currentTime.format(timeFormatter);

        //add ride to orders with rideId and userId


        Query query = ordersRef.orderByChild("rideId").equalTo(ride.getPushId());
        query.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(getApplication().getApplicationContext(), "Order exists for this ride!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String key = ordersRef.push().getKey();
                    ordersRef.child(key).child("userId").setValue(firebaseUser.getUid());
                    ordersRef.child(key).child("riderName").setValue(rider.getName());
                    ordersRef.child(key).child("status").setValue("pending");
                    ordersRef.child(key).child("driverId").setValue(ride.getDriverId());
                    ordersRef.child(key).child("paymentMethod").setValue(paymentMethod);
                    ordersRef.child(key).child("pushId").setValue(key);
                    ordersRef.child(key).child("date").setValue(formattedDate);
                    ordersRef.child(key).child("time").setValue(formattedTime);
                    ordersRef.child(key).child("rideId").setValue(ride.getPushId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplication().getApplicationContext(), "Order created Successfully!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
