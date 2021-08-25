package com.example.cloudclinic_said;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignupPatientSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_patient_success);
        Button btnsuccess=findViewById(R.id.btn_getStarted_patient);

        btnsuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent proceed=new Intent(SignupPatientSuccessActivity.this,PatientHomepageActivity.class);
                startActivity(proceed);
            }
        });
    }
}
