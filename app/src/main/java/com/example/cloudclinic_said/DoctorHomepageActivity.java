package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;

import com.example.cloudclinic_said.adapter.DeviceFragmentPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class DoctorHomepageActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Button btn_upcoming_appointment;
    FirebaseAuth mFirebaseAuth;
    String userID;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_homepage);
        tabLayout=findViewById(R.id.tabs_doctor);
        viewPager=findViewById(R.id.view_pager_doctor);
//        setSupportActionBar(toolbar);
        mFirebaseAuth=FirebaseAuth.getInstance();

        userID=mFirebaseAuth.getCurrentUser().getUid();
        setupViewPager();
        tabLayout.getTabAt(0).setIcon(R.drawable.actions_calendar);
        tabLayout.getTabAt(1).setIcon(R.drawable.action_settings);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (task.isSuccessful() && task.getResult() != null){
                    sendFCMTokenToDatabase(task.getResult().getToken());
                }
            }
        });

    }

    private void setupViewPager(){
        DeviceFragmentPagerAdapter adapter=new DeviceFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AppointmentFragment(),"Appointment");
        adapter.addFrag(new SettingFragment(),"Setting");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void sendFCMTokenToDatabase(String token){

        FirebaseFirestore mFirestore =FirebaseFirestore.getInstance();
        DocumentReference documentReference=mFirestore.collection("DoctorProfile").document(userID);

    }
}
