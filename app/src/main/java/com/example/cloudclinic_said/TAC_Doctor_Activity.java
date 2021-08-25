package com.example.cloudclinic_said;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class TAC_Doctor_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tac__doctor_);

        final EditText et_phoneNumber = findViewById(R.id.et_phoneNumber);
        final Button btn_generateOtp_doctor = findViewById(R.id.btn_generateOtp_doctor);
        String mobiless = getIntent().getStringExtra("mobile");
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        et_phoneNumber.setText(mobiless);

        btn_generateOtp_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_phoneNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(TAC_Doctor_Activity.this, "Enter Mobile",Toast.LENGTH_SHORT).show();
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);
                btn_generateOtp_doctor.setVisibility((View.INVISIBLE));

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+60" + et_phoneNumber.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        TAC_Doctor_Activity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential){
//                                progressBar.setVisibility(View.GONE);
                                btn_generateOtp_doctor.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e ){
//                                progressBar.setVisibility(View.GONE);
                                btn_generateOtp_doctor.setVisibility(View.VISIBLE);
                                Toast.makeText(TAC_Doctor_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,@NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){
//                                progressBar.setVisibility(View.GONE);
                                btn_generateOtp_doctor.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), EnterOTPDoctorActivity.class);
                                intent.putExtra("mobile",et_phoneNumber.getText().toString());
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }

                        }
                );

            }
        });

        /**final Button btn_generateOTP=findViewById(R.id.btn_generateOtp_doctor);

         btn_generateOTP.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent generateIntent=new Intent(TAC_Doctor_Activity.this,EnterOTPDoctorActivity.class);
        startActivity(generateIntent);
        }
        });*/
    }
}
