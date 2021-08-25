package com.example.cloudclinic_said;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterOTPDoctorActivity extends AppCompatActivity {

    private EditText et_enterOtp1, et_enterOtp2, et_enterOtp3, et_enterOtp4, et_enterOtp5, et_enterOtp6;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpdoctor);

        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+60-%s",getIntent().getStringExtra("mobile")
        ));

        et_enterOtp1=findViewById(R.id.et_enterOtp1);
        et_enterOtp2=findViewById(R.id.et_enterOtp2);
        et_enterOtp3=findViewById(R.id.et_enterOtp3);
        et_enterOtp4=findViewById(R.id.et_enterOtp4);
        et_enterOtp5=findViewById(R.id.et_enterOtp5);
        et_enterOtp6=findViewById(R.id.et_enterOtp6);

        setupOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button btn_verify_otp = findViewById(R.id.btn_verifyOtp);

        verificationId = getIntent().getStringExtra("verificationId");

        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_enterOtp1.getText().toString().trim().isEmpty()
                        || et_enterOtp2.getText().toString().trim().isEmpty()
                        || et_enterOtp3.getText().toString().trim().isEmpty()
                        || et_enterOtp4.getText().toString().trim().isEmpty()
                        || et_enterOtp5.getText().toString().trim().isEmpty()
                        || et_enterOtp6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EnterOTPDoctorActivity.this, "Please Enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code =
                        et_enterOtp1.getText().toString()+
                                et_enterOtp2.getText().toString()+
                                et_enterOtp3.getText().toString()+
                                et_enterOtp4.getText().toString()+
                                et_enterOtp5.getText().toString()+
                                et_enterOtp6.getText().toString();

                if(verificationId != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    btn_verify_otp.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    btn_verify_otp.setVisibility((View.VISIBLE));
                                    if(task.isSuccessful()){
                                        //check kat mainactivity class tu cam salah(maybe tuka ke success otp)
                                        Intent intent = new Intent(getApplicationContext(), OTPSuccessDoctorActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(EnterOTPDoctorActivity.this, "the verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        findViewById(R.id.txt_resendOTP).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+60" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        EnterOTPDoctorActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential){

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e ){

                                Toast.makeText(EnterOTPDoctorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId,@NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){

                                verificationId = newVerificationId;
                                Toast.makeText(EnterOTPDoctorActivity.this,"OTP Sent", Toast.LENGTH_SHORT).show();
                            }

                        }
                );
            }
        });



        /**final Button btn_verify_otp=findViewById(R.id.btn_verifyOtp);

         btn_verify_otp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent verifyIntent=new Intent(EnterOTPDoctorActivity.this,OTPSuccessDoctorActivity.class);
        startActivity(verifyIntent);
        }
        });*/
    }

    private void setupOTPInputs(){
        et_enterOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    et_enterOtp2.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_enterOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    et_enterOtp3.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_enterOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    et_enterOtp4.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_enterOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    et_enterOtp5.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_enterOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    et_enterOtp6.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
