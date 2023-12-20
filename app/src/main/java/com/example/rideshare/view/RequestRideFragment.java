package com.example.rideshare.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.rideshare.R;
import com.example.rideshare.databinding.FragmentRequestRideBinding;

import java.util.Calendar;

public class RequestRideFragment extends Fragment {
    FragmentRequestRideBinding binding;
    private String[] items = {"Ain Shams University Gate 3", "Ain Shams University Gate 4", "October City", "Maadi", "Nasr City","Haram", "Fifth Settlement", "Zayed City"};
    private String[] times = {"7:30 AM", "5:30 PM"};
    private ArrayAdapter<String> itemsAdapter;
    private ArrayAdapter<String> timesAdapter;
    private DatePickerDialog datePickerDialog;
    private String src;
    private  String dest;
    private String date;
    private String time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRequestRideBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_list_item, items);
        timesAdapter = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_list_item, times);
        binding.autoCompSource.setAdapter(itemsAdapter);
        binding.autoCompDest.setAdapter(itemsAdapter);
        binding.autoCompTime.setAdapter(timesAdapter);
        binding.dateBtn.setText(getTodaysDate());

        initDatePicker();
        binding.autoCompSource.setOnItemClickListener((adapterView, view1, i, l) -> {
            src = adapterView.getItemAtPosition(i).toString();
        });
        binding.autoCompDest.setOnItemClickListener((adapterView, view1, i, l) -> {
            dest = adapterView.getItemAtPosition(i).toString();
        });
        binding.autoCompTime.setOnItemClickListener((adapterView, view1, i, l) -> {
            time = adapterView.getItemAtPosition(i).toString();
        });
        binding.searchBtn.setOnClickListener(view1 -> {
            if(src == null || dest == null || date == null || time == null ||
                    src.isEmpty() || dest.isEmpty() || date.isEmpty() || time.isEmpty())
                Toast.makeText(requireActivity(), "Please enter all fields", Toast.LENGTH_LONG).show();
            else {
                NavDirections action = RequestRideFragmentDirections.actionRequestRideFragmentToRidesListFragment(
                        src, dest, date, time);
                Navigation.findNavController(view1).navigate(action);
            }
        });
        binding.dateBtn.setOnClickListener(view1 -> datePickerDialog.show());
        binding.ridesBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(RequestRideFragmentDirections.actionRequestRideFragmentToRidesListFragment(
                    null, null, null, null
            ));
        });
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

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                date = makeDateString(day, month, year);
                binding.dateBtn.setText(date);

            }
        };
        date = getTodaysDate();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(requireActivity(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return month + "/" + day + "/" + year;
    }

}
