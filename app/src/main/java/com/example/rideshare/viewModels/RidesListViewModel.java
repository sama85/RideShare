package com.example.rideshare.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rideshare.models.Ride;

import java.util.ArrayList;
import java.util.List;

public class RidesListViewModel extends AndroidViewModel{
    private final MutableLiveData<List<Ride>> rides = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<List<Ride>> getRides() {
        return rides;
    }
    public RidesListViewModel(@NonNull Application application) {
        super(application);
        rides.getValue().add(new Ride("Samy Ahmed", "Maadi", "Ain Shams University, Gate 4", "4-12-2023", "Depart at: 7:30 am", 150));
        rides.getValue().add(new Ride("Waleed Ahmed", "October City", "Ain Shams University, Gate 3", "3-12-2023", "Depart at: 7:30 am", 300));
        rides.getValue().add(new Ride("Mariam Osama", "Ain Shams University, Gate 3","October City", "6-12-2023", "Depart at: 5:30 pm", 350));
    }

}
