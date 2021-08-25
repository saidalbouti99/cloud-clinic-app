package com.example.cloudclinic_said;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OTPSuccessDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpsuccess_doctor);
        Button btn_otp_success=findViewById(R.id.btn_getStarted_doctor);

        btn_otp_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getStartedIntent=new Intent(OTPSuccessDoctorActivity.this,DoctorHomepageActivity.class);
                startActivity(getStartedIntent);
            }
        });
    }
}
