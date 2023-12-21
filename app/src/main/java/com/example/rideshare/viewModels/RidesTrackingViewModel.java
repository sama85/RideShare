package com.example.rideshare.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rideshare.models.Order;
import com.example.rideshare.models.Ride;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RidesTrackingViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Ride>> rides = new MutableLiveData<>(new ArrayList<>());
    private DatabaseReference ordersRef;
    private DatabaseReference ridesRef;
    private MutableLiveData<List<String>> ridesId = new MutableLiveData<>(new ArrayList<>());
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    public List<Order> orders = new ArrayList<>();

    public MutableLiveData<List<Ride>> getRides() {
        return rides;
    }
    public MutableLiveData<List<String>> getRideIds() {
        return ridesId;
    }
    public RidesTrackingViewModel(@NonNull Application application) {
        super(application);
        ordersRef = FirebaseDatabase.getInstance("https://rideshareapp-authentication-default-rtdb.europe-west1.firebasedatabase.app/").getReference("orders");
        ridesRef = FirebaseDatabase.getInstance("https://rideshareapp-authentication-default-rtdb.europe-west1.firebasedatabase.app/").getReference("rides");
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        fetchIds();
    }

    public void fetchIds(){
        /** add logic to:
         *      1.  get all ride IDs of this rider in a list
         *      2. loop through ids and fetch value (add value listener) for each id
         * */
        Query query = ordersRef.orderByChild("userId").equalTo(firebaseUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    List<String> ids = new ArrayList<>();
                    List<Order> orders_list = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String id = dataSnapshot.child("rideId").getValue(String.class);
                        String requestStatus = dataSnapshot.child("status").getValue(String.class);
                        String paymentMethod = dataSnapshot.child("paymentMethod").getValue(String.class);
                        String pushId = dataSnapshot.child("pushId").getValue(String.class);

                        Order order = new Order(paymentMethod, requestStatus, pushId);
                        if(id != null) {
                            ids.add(id);
                            orders_list.add(order);
                        }
                    }
                    orders = orders_list;
                    ridesId.setValue(ids);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FirebaseData", "onCancelled", error.toException());
            }
        });
    }

    public void fetchRides(List<String> ids) {
            ridesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        List<Ride> ridesList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Ride ride = dataSnapshot.getValue(Ride.class);
                            for(String id : ids) {
                                //search ride in ids
                                if (ride != null && dataSnapshot.getKey().equals(id)) {
                                    ridesList.add(ride);
                                    int idx = ridesId.getValue().indexOf(id);
                                    Order order = orders.get(idx);
                                    checkExpiryTime(ride, order);

                                }
                            }
                        }
                        rides.setValue(ridesList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("FirebaseData", "onCancelled", error.toException());
                }
            });
        }


    public void cancelOrder(Order order){
        /** rider can only cancel when order is pending from xml cancel btn attribute */
        ordersRef.child(order.getPushId()).child("status").setValue("cancelled");
    }

    private void checkExpiryTime(Ride ride, Order order) {
        if(order.getStatus().equals("pending") || order.getStatus().equals("confirmed")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("h:mm a");

            LocalDate rideDate = LocalDate.parse(ride.getDate(), formatter);
            LocalTime rideTime = LocalTime.parse(ride.getTime(), timeFormatter);
            LocalDateTime rideDateTime = LocalDateTime.of(rideDate, rideTime);
            LocalTime deadlineTime;

            LocalDateTime deadlineDateTime;

            // Get the current date and time for comparison with request deadline
            LocalDateTime currentDateTime = LocalDateTime.now();
            if(order.getStatus().equals("pending")) {
                if (ride.getTime().contains("PM")) {
                    deadlineTime = LocalTime.parse("4:30 PM", timeFormatter);
                    deadlineDateTime = LocalDateTime.of(rideDate, deadlineTime);
                } else {
                    deadlineTime = LocalTime.parse("11:30 PM", timeFormatter);
                    deadlineDateTime = LocalDateTime.of(rideDate.minusDays(1), deadlineTime);
                }
                if (currentDateTime.isAfter(deadlineDateTime) && currentDateTime.isBefore(rideDateTime))
                    ordersRef.child(order.getPushId()).child("status").setValue("expired");
            }
            else if(currentDateTime.isAfter(rideDateTime))
                ordersRef.child(order.getPushId()).child("status").setValue("completed");
        }
    }
}
