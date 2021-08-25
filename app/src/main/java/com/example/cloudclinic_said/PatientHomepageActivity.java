package com.example.cloudclinic_said;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.cloudclinic_said.adapter.DeviceFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class PatientHomepageActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_homepage);

        tabLayout=findViewById(R.id.tabs_patient);
        viewPager=findViewById(R.id.view_pager_patient);
//        setSupportActionBar(toolbar);

        setupViewPager();
        tabLayout.getTabAt(0).setIcon(R.drawable.actions_calendar);
        tabLayout.getTabAt(1).setIcon(R.drawable.action_settings);

    }
    private void setupViewPager(){
        DeviceFragmentPagerAdapter adapter=new DeviceFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AppointmentPatientFragment(),"Appointment");
        adapter.addFrag(new SettingPatientFragment(),"Setting");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
