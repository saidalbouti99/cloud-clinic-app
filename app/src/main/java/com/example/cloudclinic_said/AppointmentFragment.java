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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppointmentFragment extends Fragment {

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID;
    public AppointmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_appointment,container,false);
        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        mFirebaseAuth=FirebaseAuth.getInstance();

        userID=mFirebaseAuth.getCurrentUser().getUid();

        Button button = (Button) rootView.findViewById(R.id.btn_upcoming_appointment_patient);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getActivity(), UpcomingAppointmentDoctorActivity.class);
                startActivity(intent);

                Intent intentPassAppID = new Intent(getActivity(),UpcomingAppointmentDoctorActivity.class);
                intentPassAppID.putExtra("EXTRA_USER_ID", userID);
                startActivity(intentPassAppID);
            }
        });

//        Button btnJoin=rootView.findViewById(R.id.btn_join_appointment_doctor);
//        btnJoin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2;
//                intent2=new Intent(getActivity(),DashboardActivity.class);
//                startActivity(intent2);
//            }
//        });

        return rootView;



    }
}
