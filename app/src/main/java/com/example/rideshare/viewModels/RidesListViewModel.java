package com.example.rideshare.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rideshare.models.Ride;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RidesListViewModel extends AndroidViewModel{
    private final MutableLiveData<List<Ride>> rides = new MutableLiveData<>(new ArrayList<>());
    private DatabaseReference ridesRef;
    public MutableLiveData<List<Ride>> getRides() {
        return rides;
    }
    public RidesListViewModel(@NonNull Application application) {
        super(application);
        ridesRef = FirebaseDatabase.getInstance("https://rideshareapp-authentication-default-rtdb.europe-west1.firebasedatabase.app/").getReference("rides");
        fetchRides();
        //addDummyRides();
    }

    private void addDummyRides() {
        rides.getValue().add(new Ride("Samy Ahmed", "Maadi", "Ain Shams University, Gate 4", "4-12-2023", "Depart at: 7:30 am", 150L));
        rides.getValue().add(new Ride("Waleed Ahmed", "October City", "Ain Shams University, Gate 3", "3-12-2023", "Depart at: 7:30 am", 300L));
        rides.getValue().add(new Ride("Mariam Osama", "Ain Shams University, Gate 3","October City", "6-12-2023", "Depart at: 5:30 pm", 350L));
    }

    void fetchRides(){
        ridesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    List<Ride> ridesList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        String src = ride.getSrc();
                        Log.i("ride", "ride src: " + src);
                       // ridesList.add(ride);
                        ridesList.add(ride);
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

    void fetchRidesByRequest(){

        // Create a query to find the ride with the desired source value
        Query query = ridesRef.orderByChild("src_dest").equalTo("Maadi" + "_" + "Ain Shams University Gate 3");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Ride> ridesList = rides.getValue();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Ride ride = snapshot.getValue(Ride.class);
                        if (ride != null) {
                            ridesList.add(ride);
                        }
                    }
                    rides.setValue(ridesList);
                } else {
                    // Handle case where no ride with the desired source value exists
                    Log.d("FirebaseData", "No ride found");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors
                Log.w("FirebaseData", "onCancelled", databaseError.toException());
            }
        });

    }

}
