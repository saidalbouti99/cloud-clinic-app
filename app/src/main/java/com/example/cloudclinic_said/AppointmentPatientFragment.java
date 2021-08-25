package com.example.cloudclinic_said;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppointmentPatientFragment extends Fragment {

    public AppointmentPatientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_appointment,container,false);
        View rootView = inflater.inflate(R.layout.fragment_appointment_patient, container, false);

        Button btn_upcoming_appointment = (Button) rootView.findViewById(R.id.btn_upcoming_appointment_patient);
        btn_upcoming_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getActivity(), UpcomingAppointmentPatientActivity.class);
                startActivity(intent);
            }
        });

        Button btn_book_appointment = (Button) rootView.findViewById(R.id.btn_book_appointment_patient);
        btn_book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBook;
                intentBook = new Intent(getActivity(), BookForLaterActivity.class);
                startActivity(intentBook);
            }
        });

//        Button btn_join_appointment = (Button) rootView.findViewById(R.id.btn_join_appointment_patient);
//        btn_join_appointment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentBook;
//                intentBook = new Intent(getActivity(), DashboardActivity.class);
//                startActivity(intentBook);
//            }
//        });
//

        return rootView;


    }
}
