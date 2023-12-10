package com.example.rideshare.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rideshare.models.Ride;

import java.util.ArrayList;
import java.util.List;

public class RidesListViewModel extends ViewModel {
    List<Ride> rides = new ArrayList<>();
    public RidesListViewModel() {
        super();
        rides.add(new Ride("Samy Ahmed", "Maadi", "Ain Shams University, Gate 4", "4-12-2023", "Depart at: 7:30 am", 150));
        rides.add(new Ride("Waleed Ahmed", "October City", "Ain Shams University, Gate 3", "3-12-2023", "Depart at: 7:30 am", 300));
        rides.add(new Ride("Mariam Osama", "Ain Shams University, Gate 3","October City", "6-12-2023", "Depart at: 5:30 pm", 350));
    }
    public List<Ride> getRides() {
        return rides;
    }
}
