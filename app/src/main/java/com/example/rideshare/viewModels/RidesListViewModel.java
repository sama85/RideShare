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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
    }

    public void fetchRides(){
        ridesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    List<Ride> ridesList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        if(ride != null && ride.getStatus().equals("available")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                            LocalDate todaysDate = LocalDate.parse(getTodaysDate(), formatter);
                            LocalDate rideDate = LocalDate.parse(ride.getDate(), formatter);
                            if(!checkExpired(ride))
                                ridesList.add(ride);
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

    public void fetchRides(String src, String dest, String date, String time){

        // Create a query to find the ride with the desired source value
        Query query = ridesRef.orderByChild("src").equalTo(src);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Ride> ridesList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Ride ride = snapshot.getValue(Ride.class);
                        if (ride != null  && ride.getDest().equals(dest)
                                && ride.getDate().equals(date)  && ride.getTime().equals(time)
                                && ride.getStatus().equals("available")
                                && !checkExpired(ride)
                        ) {
                            Log.i("ride", "src: " + ride.getSrc());
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

    private boolean checkExpired(Ride ride) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("h:mm a");

        LocalDate deadlineDate = LocalDate.parse(ride.getDate(), formatter);
        LocalTime deadlineTime;

        LocalDateTime deadlineDateTime;

        // Get the current date and time for comparison with request deadline
        LocalDateTime currentDateTime = LocalDateTime.now();
        if(ride.getTime().contains("PM")){
            deadlineTime = LocalTime.parse("4:30 PM", timeFormatter);
            deadlineDateTime = LocalDateTime.of(deadlineDate, deadlineTime);
        }
        else{
            deadlineTime = LocalTime.parse("11:30 PM", timeFormatter);
            deadlineDateTime = LocalDateTime.of(deadlineDate.minusDays(1), deadlineTime);
        }
        if(currentDateTime.isAfter(deadlineDateTime))
            return true;

        else return false;
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private String makeDateString(int day, int month, int year)
    {
        return month + "/" + day + "/" + year;
    }

}
